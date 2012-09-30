package org.rest.sec.client.template;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.common.client.template.AbstractClientRestTemplate;
import org.rest.common.security.PreemptiveAuthHttpRequestFactory;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
@Profile("client")
public class UserClientRestTemplate extends AbstractClientRestTemplate<User> {

    @Autowired
    private SecBusinessPaths paths;

    public UserClientRestTemplate() {
        super(User.class);
    }

    // operations

    // template method

    @Override
    public final String getUri() {
        return paths.getUserUri();
    }

    @Override
    protected void basicAuth(final String username, final String password) {
        Preconditions.checkState((username == null && password == null) || (username != null && password != null));
        final String usernameToUse = (username != null) ? username : SecurityConstants.ADMIN_USERNAME;
        final String passwordToUse = (username != null) ? password : SecurityConstants.ADMIN_PASSWORD;

        final PreemptiveAuthHttpRequestFactory requestFactory = ((PreemptiveAuthHttpRequestFactory) restTemplate.getRequestFactory());
        ((DefaultHttpClient) requestFactory.getHttpClient()).getCredentialsProvider().setCredentials(requestFactory.getAuthScope(), new UsernamePasswordCredentials(usernameToUse, passwordToUse));
    }

}
