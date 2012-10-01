package org.rest.sec.persistence.service;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.model.Principal;
import org.rest.sec.model.PrincipalEntityOpsImpl;
import org.rest.sec.test.SecSearchPersistenceIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrincipalSearchPersistenceIntegrationTest extends SecSearchPersistenceIntegrationTest<Principal> {

    @Autowired
    private IPrincipalService principalService;
    @Autowired
    private PrincipalEntityOpsImpl entityOps;

    // tests

    // template method

    @Override
    protected final IPrincipalService getApi() {
        return principalService;
    }

    @Override
    protected final Principal createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final IEntityOperations<Principal> getEntityOps() {
        return entityOps;
    }

}
