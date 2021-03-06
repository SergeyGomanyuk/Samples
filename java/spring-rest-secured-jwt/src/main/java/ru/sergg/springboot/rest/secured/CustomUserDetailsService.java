package ru.sergg.springboot.rest.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Profile("!disabled-security")
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            if("client".equals(username)) return new User(username, passwordEncoder.encode("client-password"), Collections.EMPTY_LIST);
            if(!"admin".equals(username) && !"user".equals(username)) throw new Exception();
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+username));
            if("admin".equals(username)) {
                grantedAuthorities.add(new SimpleGrantedAuthority("authority"));
                grantedAuthorities.add(new SimpleGrantedAuthority("Admin"));
            }
            for(int i=0; i<14; i++) {
                grantedAuthorities.add(new SimpleGrantedAuthority("test-auth-123456789-" + i));
            }

//            AuthorityUtils.createAuthorityList("");
            return new User(username, passwordEncoder.encode(username + "-password"), grantedAuthorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database", e);
        }
    }
}