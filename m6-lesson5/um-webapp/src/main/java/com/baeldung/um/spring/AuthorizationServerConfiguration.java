package com.baeldung.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${signing-key:oui214hmui23o4hm1pui3o2hp4m1o3h2m1o43}")
    private String signingKey;

    public AuthorizationServerConfiguration() {
        super();
    }

    // beans

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // config

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
        .withClient("live-test")
        .secret("bGl2ZS10ZXN0")
        .authorizedGrantTypes("password")
        .scopes("um-webapp", "read", "write", "trust")
        .autoApprove("um-webapp")
        .accessTokenValiditySeconds(3600)
        .and()
        //
        .withClient("um")
        .secret("VXB0YWtlLUlyb24h")
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("um-webapp", "read", "write", "trust")
        .refreshTokenValiditySeconds(3600 * 24)
        .autoApprove("um-webapp")
        .accessTokenValiditySeconds(3600)
        ;
        // @formatter:on
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {// @formatter:off
        endpoints.
        tokenStore(tokenStore()).
        authenticationManager(authenticationManager).
        userDetailsService(userDetailsService).
        allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).
        accessTokenConverter(accessTokenConverter());
    }// @formatter:on

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
        super.configure(security);
    }

}
