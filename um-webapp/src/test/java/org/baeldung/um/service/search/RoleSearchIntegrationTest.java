package org.baeldung.um.service.search;

import org.baeldung.um.common.FixtureEntityFactory;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.service.IRoleService;
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
