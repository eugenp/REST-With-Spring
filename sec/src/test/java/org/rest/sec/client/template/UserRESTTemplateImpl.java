package org.rest.sec.client.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.common.client.template.AbstractRESTTemplate;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.Role;
import org.rest.sec.model.dto.User;
import org.rest.testing.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.jayway.restassured.specification.RequestSpecification;

@Component
@Profile("client")
public final class UserRESTTemplateImpl extends AbstractRESTTemplate<User> {

    @Autowired protected SecBusinessPaths paths;

    public UserRESTTemplateImpl() {
        super(User.class);
    }

    // API

    // template method

    @Override
    public final String getURI() {
        return paths.getUserUri();
    }

    @Override
    public final RequestSpecification givenAuthenticated() {
        return AuthenticationUtil.givenBasicAuthenticated();
    }

    @Override
    public final User createNewEntity() {
        return new User(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    @Override
    public final void invalidate(final User entity) {
        entity.setName(null);
    }

    @Override
    public final void change(final User resource) {
        resource.setName(randomAlphabetic(8));
    }

}
