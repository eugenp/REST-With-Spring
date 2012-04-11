package org.rest.sec.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecDiscoverabilityRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class RoleDiscoverabilityRESTIntegrationTest extends SecDiscoverabilityRESTIntegrationTest<Role> {

    @Autowired
    private RoleRESTTemplateImpl restTemplate;

    public RoleDiscoverabilityRESTIntegrationTest() {
	super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewEntity() {
	return restTemplate.createNewEntity();
    }

    @Override
    protected final String getURI() {
	return getTemplate().getURI();
    }

    @Override
    protected void change(final Role resource) {
	resource.setName(randomAlphabetic(6));
    }

    @Override
    protected RequestSpecification givenAuthenticated() {
	return getTemplate().givenAuthenticated();
    }

    @Override
    protected final RoleRESTTemplateImpl getTemplate() {
	return restTemplate;
    }

}
