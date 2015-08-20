package org.baeldung.rest.service.search;

import org.baeldung.rest.common.FixtureEntityFactory;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchIntegrationTest extends SecSearchIntegrationTest<Role> {

    @Autowired
    private IRoleService roleService;

    // tests

    // template method

    @Override
    protected final IRoleService getApi() {
        return roleService;
    }

    @Override
    protected final Role createNewEntity() {
        return FixtureEntityFactory.createNewRole();
    }

}
