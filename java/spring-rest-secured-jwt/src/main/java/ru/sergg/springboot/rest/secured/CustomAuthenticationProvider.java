
package ru.sergg.springboot.rest.secured;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Profile("!disabled-security")
@Service
public class CustomAuthenticationProvider  implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication)  throws AuthenticationException {
        if(authentication == null) return null;
        String clientId = getClientId();
        String username = authentication.getName();
        if(username == null) return authentication;
        // You can get the password here
        Object credentials = authentication.getCredentials();
        if(credentials == null) return authentication;
        String password = credentials.toString();

        // Your custom authentication logic here
        if(("admin".equals(username) || "user".equals(username)) && (username+"-password").equals(password)) {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+username));
            if("admin".equals(username)) grantedAuthorities.add(new SimpleGrantedAuthority("authority"));
            // AuthorityUtils.createAuthorityList("");
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
        }

        throw new BadCredentialsException("");
//        try {
//            if("client".equals(username)) return new User(username, passwordEncoder.encode("client-password"), Collections.EMPTY_LIST);
//            if(!"admin".equals(username) && !"user".equals(username)) throw new Exception();
//            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+username));
//            if("admin".equals(username)) grantedAuthorities.add(new SimpleGrantedAuthority("authority"));
//            for(int i=0; i<14; i++) {
//                grantedAuthorities.add(new SimpleGrantedAuthority("test-auth-123456789-" + i));
//            }
//
////            AuthorityUtils.createAuthorityList("");
//            return new User(username, passwordEncoder.encode(username + "-password"), grantedAuthorities);
//        } catch (Exception e) {
//            throw new UsernameNotFoundException("User " + username + " was not found in the database", e);
//        }
    }

    private static String getClientId() {
        HttpServletRequest currentHttpRequest = getCurrentHttpRequest();
        if(currentHttpRequest == null) return null;
        String requestURI = currentHttpRequest.getRequestURI();
        if("/oauth/token".equals(requestURI)) {
            Principal userPrincipal = currentHttpRequest.getUserPrincipal();
            if(userPrincipal==null) return null;
            return userPrincipal.getName();
        } else if("/oauth/authorize".equals(requestURI)) {
            return currentHttpRequest.getParameter("client_id");
        }
        return null;
    }

    private static HttpServletRequest getCurrentHttpRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
