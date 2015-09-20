package org.baeldung.um.service.search;

import org.baeldung.um.common.FixtureEntityFactory;
import org.baeldung.um.persistence.model.Principal;
import org.baeldung.um.service.IPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;

public class PrincipalSearchIntegrationTest extends SecSearchIntegrationTest<Principal> {

    @Autowired
    private IPrincipalService principalService;

    // tests

    // template method

    @Override
    protected final IPrincipalService getApi() {
        return principalService;
    }

    @Override
    protected final Principal createNewEntity() {
        return FixtureEntityFactory.createNewPrincipal();
    }

}
