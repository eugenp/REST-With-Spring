package org.rest.sec.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleTestRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecDiscoverabilityRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class RoleDiscoverabilityRestIntegrationTest extends SecDiscoverabilityRestIntegrationTest<Role> {

    @Autowired
    private RoleTestRestTemplate restTemplate;
    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleDiscoverabilityRestIntegrationTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected void change(final Role resource) {
        resource.setName(randomAlphabetic(6));
    }

    @Override
    protected RequestSpecification givenAuthenticated() {
        return getApi().givenAuthenticated();
    }

    @Override
    protected final RoleTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
