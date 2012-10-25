package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleClientRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleLogicClientRestLiveTest extends SecLogicClientRestLiveTest<Role> {

    @Autowired
    private RoleClientRestTemplate api;
    @Autowired
    private RoleEntityOpsImpl entityOps;

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
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }
}
