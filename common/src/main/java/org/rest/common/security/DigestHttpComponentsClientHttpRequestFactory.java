package org.rest.common.security;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class DigestHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private HttpContext httpContext;

    public DigestHttpComponentsClientHttpRequestFactory() {
        super();
    }

    public DigestHttpComponentsClientHttpRequestFactory(final HttpClient httpClient) {
        super(httpClient);
    }

    // API

    @Override
    protected final HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
        return httpContext;
    }

    public final void setHttpContext(final HttpContext httpContextToSet) {
        httpContext = httpContextToSet;
    }

}
