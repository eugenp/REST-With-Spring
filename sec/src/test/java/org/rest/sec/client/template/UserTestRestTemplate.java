package org.rest.sec.client.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.client.template.AbstractTestRestTemplate;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.dto.User;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
    public final String getUri() {
        return paths.getUserUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS);
    }

}
