package org.rest.sec.web.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecDiscoverabilityRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class UserDiscoverabilityRESTIntegrationTest extends SecDiscoverabilityRESTIntegrationTest<User> {

    @Autowired
    private UserRESTTemplateImpl restTemplate;

    public UserDiscoverabilityRESTIntegrationTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final String getURI() {
        return getAPI().getURI();
    }

    @Override
    protected final void change(final User resource) {
        resource.setName(randomAlphabetic(6));
    }

    @Override
    protected final User createNewEntity() {
        return restTemplate.createNewEntity();
    }

    @Override
    protected final RequestSpecification givenAuthenticated() {
        return getAPI().givenAuthenticated();
    }

    @Override
    protected final UserRESTTemplateImpl getAPI() {
        return restTemplate;
    }

}
