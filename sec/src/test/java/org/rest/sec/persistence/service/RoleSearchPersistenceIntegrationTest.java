package org.rest.sec.persistence.service;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecSearchPersistenceIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchPersistenceIntegrationTest extends SecSearchPersistenceIntegrationTest<Role> {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private RoleEntityOpsImpl entityOps;

    // tests

    // template method

    @Override
    protected final IRoleService getApi() {
        return roleService;
    }

    @Override
    protected final Role createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
