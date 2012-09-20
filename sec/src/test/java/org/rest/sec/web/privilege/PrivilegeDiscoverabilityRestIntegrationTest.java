package org.rest.sec.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeTestRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecDiscoverabilityRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegeDiscoverabilityRestIntegrationTest extends SecDiscoverabilityRestIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate restTemplate;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeDiscoverabilityRestIntegrationTest() {
        super(Privilege.class);
    }

    // tests

    // template method

    @Override
    protected final Privilege createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getURI() {
        return getAPI().getURI();
    }

    @Override
    protected void change(final Privilege resource) {
        resource.setName(randomAlphabetic(6));
    }

    @Override
    protected RequestSpecification givenAuthenticated() {
        return getAPI().givenAuthenticated();
    }

    @Override
    protected final PrivilegeTestRestTemplate getAPI() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
