package com.baeldung.um.client.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.baeldung.test.common.client.template.AbstractRestClient;
import com.baeldung.um.client.UmPaths;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.util.Um;

@Component
@Profile("client")
public final class UserTestRestTemplate extends AbstractRestClient<User> {

    @Autowired
    protected UmPaths paths;

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
        return new ImmutablePair<String, String>(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}
