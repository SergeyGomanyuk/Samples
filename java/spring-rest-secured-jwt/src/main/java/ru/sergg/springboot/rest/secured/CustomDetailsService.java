/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package ru.sergg.springboot.rest.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            if("jnetx".equals(username)) return new User(username, passwordEncoder.encode("jnetx"), Collections.EMPTY_LIST);
            if(!"admin".equals(username) && ! "user".equals(username)) throw new Exception();
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+username));
            if("admin".equals(username)) grantedAuthorities.add(new SimpleGrantedAuthority("kuku"));
//            AuthorityUtils.createAuthorityList("");
            return new User(username, passwordEncoder.encode("password"), grantedAuthorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database", e);
        }
    }
}