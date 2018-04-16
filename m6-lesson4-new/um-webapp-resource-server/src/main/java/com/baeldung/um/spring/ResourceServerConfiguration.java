package com.baeldung.um.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan({ "com.baeldung.um.security" })
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {    

    public ResourceServerConfiguration() {
        super();
    }        

    // http security concerns

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.
        authorizeRequests().
        anyRequest().authenticated().and().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
        csrf().disable();
        // @formatter:on
    }
    
    @Bean
    public ResourceServerTokenServices remoteTokenService() {
       RemoteTokenServices tokenServices = new RemoteTokenServices();
       tokenServices.setClientId("live-test");
       tokenServices.setClientSecret("bGl2ZS10ZXN0");       
       tokenServices.setCheckTokenEndpointUrl("http://localhost:8082/um-webapp-auth-server/oauth/check_token");
       return tokenServices;
    }

}