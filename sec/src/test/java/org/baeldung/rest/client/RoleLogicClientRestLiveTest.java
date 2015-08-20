package org.baeldung.rest.client;

import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.model.RoleDtoOpsImpl;
import org.baeldung.rest.test.live.SecLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleLogicClientRestLiveTest extends SecLogicClientRestLiveTest<Role> {

    @Autowired
    private RoleClientRestTemplate api;
    @Autowired
    private RoleDtoOpsImpl entityOps;

    public RoleLogicClientRestLiveTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleClientRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }
}
