package org.rest.common.client.security;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.common.security.PreemptiveAuthHttpRequestFactory;
import org.rest.common.spring.CommonSpringProfileUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;

@Component
@Profile(CommonSpringProfileUtil.CLIENT)
public class ClientAuthenticator {

    public ClientAuthenticator() {
        super();
    }

    // API

    public final void givenAuthenticated(final RestTemplate restTemplate, final String username, final String password) {
        if (isBasicAuth()) {
            basicAuth(restTemplate, username, password);
        } else {
            digestAuth(username, password);
        }
    }

    protected final boolean isBasicAuth() {
        return true;
    }

    final void basicAuth(final RestTemplate restTemplate, final String username, final String password) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);

        final PreemptiveAuthHttpRequestFactory requestFactory = ((PreemptiveAuthHttpRequestFactory) restTemplate.getRequestFactory());
        ((DefaultHttpClient) requestFactory.getHttpClient()).getCredentialsProvider().setCredentials(requestFactory.getAuthScope(), new UsernamePasswordCredentials(username, password));
    }

    @SuppressWarnings("unused")
    protected final void digestAuth(final String username, final String password) {
        throw new UnsupportedOperationException();
    }

}
