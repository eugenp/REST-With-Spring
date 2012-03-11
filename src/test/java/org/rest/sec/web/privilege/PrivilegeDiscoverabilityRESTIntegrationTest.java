package org.rest.sec.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.client.template.impl.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecDiscoverabilityRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegeDiscoverabilityRESTIntegrationTest extends SecDiscoverabilityRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeRESTTemplateImpl restTemplate;

    public PrivilegeDiscoverabilityRESTIntegrationTest() {
	super(Privilege.class);
    }

    // tests

    // template method

    @Override
    protected final Privilege createNewEntity() {
	return restTemplate.createNewEntity();
    }

    @Override
    protected final String getURI() {
	return getTemplate().getURI();
    }

    @Override
    protected void change(final Privilege resource) {
	resource.setName(randomAlphabetic(6));
    }

    @Override
    protected RequestSpecification givenAuthenticated() {
	return getTemplate().givenAuthenticated();
    }

    @Override
    protected final PrivilegeRESTTemplateImpl getTemplate() {
	return restTemplate;
    }

}
