package ru.sergg.springboot.rest.secured;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Profile("!disabled-security")
@Configuration
@EnableResourceServer
public  class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Value("${ru.sergg.springboot.rest.secured.publicEndpoints:}")
    private String[] publicEndpoints;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(publicEndpoints).permitAll()
                .anyRequest().authenticated();
    }

}
