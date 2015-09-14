package org.baeldung.rest.service.search;

import org.baeldung.rest.common.FixtureEntityFactory;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchIntegrationTest extends SecSearchIntegrationTest<Privilege> {

    @Autowired
    private IPrivilegeService privilegeService;

    // tests

    // template method

    @Override
    protected final IPrivilegeService getApi() {
        return privilegeService;
    }

    @Override
    protected final Privilege createNewEntity() {
        return FixtureEntityFactory.createNewPrivilege();
    }

}
