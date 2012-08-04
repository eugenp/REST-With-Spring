package org.rest.common.security;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class BasicHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    public BasicHttpComponentsClientHttpRequestFactory() {
        super();
    }

    public BasicHttpComponentsClientHttpRequestFactory(final HttpClient httpClient) {
        super(httpClient);
    }

    // API

    @Override
    protected final HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
        return super.createHttpContext(httpMethod, uri);
    }

}
