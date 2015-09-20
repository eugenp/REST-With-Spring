package org.baeldung.um.service.search;

import org.baeldung.um.common.FixtureEntityFactory;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.service.IPrivilegeService;
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
