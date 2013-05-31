package org.rest.common.security;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * Factory for DefaultContextHttpClient, with preemptive BASIC authentication.
 * 
 * @see DefaultContextHttpClient
 */
public class PreemptiveAuthHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
    private final HttpHost targetHost;

    public PreemptiveAuthHttpRequestFactory(final String host, final int port, final String scheme) {
        super();
        targetHost = new HttpHost(host, port, scheme);
    }

    @SuppressWarnings("unused")
    public PreemptiveAuthHttpRequestFactory(final String host, final int port, final String scheme, final ClientConnectionManager conman, final HttpParams params) {
        super();
        targetHost = new HttpHost(host, port, scheme);
    }

    // API

    public AuthScope getAuthScope() {
        return new AuthScope(targetHost.getHostName(), targetHost.getPort());
    }

    @Override
    protected HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
        final AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        final BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        final BasicHttpContext localcontext = new BasicHttpContext();
        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

        return localcontext;
    }
}
