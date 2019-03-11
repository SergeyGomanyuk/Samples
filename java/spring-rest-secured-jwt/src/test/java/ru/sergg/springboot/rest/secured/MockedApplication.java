package ru.sergg.springboot.rest.secured;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@SpringBootApplication
@Configuration
public class MockedApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockedApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString())).then(new Answer<UserDetails>() {
            @Override
            public UserDetails answer(InvocationOnMock invocationOnMock) throws Throwable {
                String userName = invocationOnMock.getArgument(0);
                Collection<GrantedAuthority> grantedAuthorities = null;
                switch (userName) {
                    case "client":
                        grantedAuthorities = Collections.EMPTY_LIST;
                        break;
                    case "admin":
                        grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_admin", "authority");
                        break;
                    case "user":
                        grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_user");
                        break;
                    default:
                        throw new UsernameNotFoundException("Unknown user " + userName);
                }
                return new User("userName", passwordEncoder.encode(userName + "-password"), grantedAuthorities);
            }
        });
        return userDetailsService;
    }

    @Bean
    @Primary
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
