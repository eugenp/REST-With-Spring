package org.rest.sec.persistence.service;

import org.rest.common.client.template.IEntityOperations;
import org.rest.persistence.AbstractSearchPersistenceIntegrationTest;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.persistence.util.FixtureFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchPersistenceIntegrationTest extends AbstractSearchPersistenceIntegrationTest<Role> {

    @Autowired private IRoleService roleService;
    @Autowired private RoleEntityOpsImpl entityOps;

    // tests

    // template method

    @Override
    protected final IRoleService getAPI() {
        return roleService;
    }

    @Override
    protected final Role createNewEntity() {
        return FixtureFactory.createNewRole();
    }

    @Override
    protected final IEntityOperations<Role> getEntityOperations() {
        return entityOps;
    }

}
