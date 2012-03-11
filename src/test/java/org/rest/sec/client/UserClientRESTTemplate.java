package org.rest.sec.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.rest.client.AbstractClientRESTTemplate;
import org.rest.client.IClientTemplate;
import org.rest.sec.dto.User;
import org.rest.sec.util.SecurityConstants;
import org.rest.security.DigestHttpComponentsClientHttpRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientRESTTemplate extends AbstractClientRESTTemplate<User> {

    @Autowired
    private ExamplePaths paths;

    @Value("${http.host}")
    private String host;
    @Value("${http.port}")
    private int port;
    @Value("${http.protocol}")
    private String protocol;

    @Value("${sec.auth.basic}")
    private boolean basicAuth;

    public UserClientRESTTemplate() {
	super(User.class);
    }

    //

    // template method

    @Override
    public final String getURI() {
	return paths.getUserUri();
    }

    @Override
    public final IClientTemplate<User> givenAuthenticated() {
	if (basicAuth) {
	    final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
	    final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
	    httpClient.getCredentialsProvider().setCredentials(new AuthScope(host, port, AuthScope.ANY_REALM), new UsernamePasswordCredentials(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD));
	} else {
	    final HttpHost targetHost = new HttpHost(host, port, protocol);

	    // Create AuthCache instance
	    final AuthCache authCache = new BasicAuthCache();
	    // Generate DIGEST scheme object, initialize it and add it to the local auth cache
	    final DigestScheme digestAuth = new DigestScheme();
	    // Suppose we already know the realm name
	    digestAuth.overrideParamter("realm", "Contacts Realm via Digest Authentication");
	    // Suppose we already know the expected nonce value
	    digestAuth.overrideParamter("nonce", "acegi");
	    authCache.put(targetHost, digestAuth);

	    // Add AuthCache to the execution context
	    final BasicHttpContext localcontext = new BasicHttpContext();
	    localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

	    final DigestHttpComponentsClientHttpRequestFactory requestFactory = (DigestHttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
	    requestFactory.setHttpContext(localcontext);
	    final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
	    httpClient.getCredentialsProvider().setCredentials(new AuthScope(host, port, AuthScope.ANY_REALM), new UsernamePasswordCredentials(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD));
	}

	return this;
    }

}
