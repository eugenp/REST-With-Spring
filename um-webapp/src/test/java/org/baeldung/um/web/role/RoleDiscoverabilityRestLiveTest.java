package org.baeldung.um.web.role;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.RoleTestRestTemplate;
import org.baeldung.um.model.Role;
import org.baeldung.um.model.RoleDtoOpsImpl;
import org.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<Role> {

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
