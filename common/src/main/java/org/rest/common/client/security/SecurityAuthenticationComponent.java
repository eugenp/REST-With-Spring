package org.rest.common.client.security;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

// @Component
public final class SecurityAuthenticationComponent {

    @Value("${http.host}")
    private String host;
    @Value("${http.port}")
    private int port;

    public SecurityAuthenticationComponent() {
        super();
    }

    // API

    public final void basicAuth(final RestTemplate restTemplate, final String username, final String password) {
        final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(host, port, AuthScope.ANY_REALM), new UsernamePasswordCredentials(username, password));
    }

}
