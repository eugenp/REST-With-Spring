package org.rest.sec.client.template;

import org.rest.common.client.template.AbstractTestRestTemplate;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.jayway.restassured.specification.RequestSpecification;

@Component
@Profile("client")
public final class UserTestRestTemplate extends AbstractTestRestTemplate<User> {

    @Autowired
    protected SecBusinessPaths paths;

    public UserTestRestTemplate() {
        super(User.class);
    }

    // API

    // template method

    @Override
    public final String getURI() {
        return paths.getUserUri();
    }

    @Override
    public final RequestSpecification givenAuthenticated(final String username, final String password) {
        Preconditions.checkState((username == null && password == null) || (username != null && password != null));
        final String usernameToUse = (username != null) ? username : SecurityConstants.ADMIN_USERNAME;
        final String passwordToUse = (username != null) ? password : SecurityConstants.ADMIN_PASSWORD;

        return auth.givenBasicAuthenticated(usernameToUse, passwordToUse);
    }

}
