package ru.sergg.springboot.rest.secured;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Profile("!disabled-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Value("${ru.sergg.springboot.rest.secured.clientIds:}")
    private String[] clientIds;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private String clientId = "clientId";
    private String clientSecret = "clientPassword";

    // TODO use java private keystore
    private String tokenSecret = "z103i!@njx32ddx3m1";
//    private String publicKey = "public key";

    @Value("${ru.sergg.springboot.rest.secured.accessTokenValiditySeconds:3600}") // one hour
    private int accessTokenValiditySeconds;

    @Value("${ru.sergg.springboot.rest.secured.refreshTokenValiditySeconds:86400}") // one date
    private int refreshTokenValiditySeconds;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
                if(accessToken.getScope() != null && accessToken.getScope().contains("openid")) {
                    String login = authentication.getUserAuthentication().getPrincipal().toString();
                    //The JWT signature algorithm we will be using to sign the token
                    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
                    //We will sign our JWT with our ApiKey secret
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenSecret);
                    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("email", login);
                    claims.put("name", "Name of " + login);
                    claims.put("username", login);
                    //Let's set the JWT Claims
                    JwtBuilder builder = Jwts.builder()
                            //                        .setHeaderParam(, )
                            .setClaims(claims)
                            //                        .setId(id)
                            //                        .setIssuedAt(now)
//                            .setSubject("olyalya")
                            //                        .setIssuer(issuer)
                            .signWith(signatureAlgorithm, signingKey);

                    Map<String, Object> additionalInfo = enhancedToken.getAdditionalInformation();
                    additionalInfo.put("id_token", builder.compact());
                }
                return enhancedToken;
            }
        };
        converter.setSigningKey(tokenSecret);
//        converter.setVerifierKey(publicKey);
        return converter;
    }
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
                //.and().authorizeRequests().antMatchers("/unsecured").permitAll();
//        security.allowFormAuthenticationForClients();
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetailsService);
        InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = clients.inMemory();
        for(String clientId : clientIds) {
            inMemoryClientDetailsServiceBuilder.withClient(clientId)
                    .secret(userDetailsService.loadUserByUsername(clientId).getPassword())
                    .scopes("read", "write", "openid", "email", "name")
                    .authorizedGrantTypes("password", "authorization_code", "implicit", "client_credentials")
//                    .authorizedGrantTypes("password", "refresh_token", "authorization_code", "implicit", "client_credentials")
                    .accessTokenValiditySeconds(accessTokenValiditySeconds)
                    .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                    .autoApprove();
        }
        inMemoryClientDetailsServiceBuilder.withClient("grafana")
                .secret(passwordEncoder.encode("grafana"))
                .authorizedGrantTypes("authorization_code")
                .autoApprove("openid")
                .scopes("openid");
//                    .authorizedGrantTypes("password", "refresh_token", "authorization_code", "implicit", "client_credentials")
//        clients.inMemory().withClient("anonymous").autoApprove(true).scopes("read", "write")
//                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
//                .refreshTokenValiditySeconds(20000);
    }
}
