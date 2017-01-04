package com.baeldung.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan({ "com.baeldung.um.security", "com.baeldung.common.security" })
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${signing-key:oui214hmui23o4hm1pui3o2hp4m1o3h2m1o43}")
    private String signingKey;

    public ResourceServerConfiguration() {
        super();
    }

    // beans

    // global security concerns

    @Bean
    public AuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    // http security concerns

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.
        authorizeRequests().
        // antMatchers("/oauth/token").permitAll().
        anyRequest().authenticated().and().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
        csrf().disable();
        // @formatter:on
    }

}