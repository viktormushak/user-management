package com.vertex.e4it.usermanagement.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final String[] permitAllMatchers = new String[]{
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/api-docs/**",
            "/users/confirm/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(permitAllMatchers).permitAll()
                .anyRequest().authenticated();
    }
}

