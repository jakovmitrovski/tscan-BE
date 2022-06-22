package com.example.squick.security.config;

import com.example.squick.security.converter.CognitoRoleConverter;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CognitoRoleConverter());

        http = http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/stripe/**").permitAll()
                .antMatchers(HttpMethod.GET, "/parkings/**").permitAll()
                .antMatchers(HttpMethod.POST, "/tickets/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tickets/**").permitAll()
                .antMatchers(HttpMethod.GET, "/ticket-generator/**").permitAll()
                .antMatchers(HttpMethod.POST, "/ticket-generator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/transactions/**").permitAll()
                .antMatchers(HttpMethod.GET, "/working-hours/**").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and();

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }
}
