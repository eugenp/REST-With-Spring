package com.baeldung.um.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan("com.baeldung.um.security")
public class UmJavaSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.
        authorizeRequests().
        // antMatchers("/api/**"). // if you want a more explicit mapping here
        anyRequest().
        authenticated().
        and().
        httpBasic().and().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  
        return http.build();
        // @formatter:on
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
