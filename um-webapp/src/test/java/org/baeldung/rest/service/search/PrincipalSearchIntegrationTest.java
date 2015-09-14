package org.baeldung.rest.service.search;

import org.baeldung.rest.common.FixtureEntityFactory;
import org.baeldung.rest.model.Principal;
import org.baeldung.rest.service.IPrincipalService;
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
