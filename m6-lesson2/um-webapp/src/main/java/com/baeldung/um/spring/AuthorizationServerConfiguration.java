package com.baeldung.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthorizationServerConfiguration() {
        super();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    // config

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
            .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
        .withClient("live-test")
        .secret("bGl2ZS10ZXN0")
        .authorizedGrantTypes("password")
        .scopes("um-webapp")
        .autoApprove("um-webapp")
        .accessTokenValiditySeconds(3600);
        // @formatter:on
    }

}
