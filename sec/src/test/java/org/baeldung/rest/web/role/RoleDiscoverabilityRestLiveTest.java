package org.baeldung.rest.web.role;

import org.baeldung.rest.client.template.RoleTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.model.RoleDtoOpsImpl;
import org.baeldung.rest.test.live.SecDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleDiscoverabilityRestLiveTest extends SecDiscoverabilityRestLiveTest<Role> {

    @Autowired
    private RoleTestRestTemplate restTemplate;
    @Autowired
    private RoleDtoOpsImpl entityOps;

    public RoleDiscoverabilityRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewEntity() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final RoleTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

}
