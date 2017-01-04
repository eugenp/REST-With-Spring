package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.httpBasic().and().authorizeRequests().antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll().anyRequest().authenticated().and().csrf().disable();
        }
    }

}
