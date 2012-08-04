package org.rest.sec.persistence.service;

import org.rest.common.client.IEntityOperations;
import org.rest.persistence.AbstractSearchPersistenceIntegrationTest;
import org.rest.sec.model.Principal;
import org.rest.sec.model.PrincipalEntityOpsImpl;
import org.rest.sec.persistence.util.FixtureFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PrincipalSearchPersistenceIntegrationTest extends AbstractSearchPersistenceIntegrationTest<Principal> {

    @Autowired private IPrincipalService principalService;
    @Autowired private PrincipalEntityOpsImpl entityOps;

    // tests

    // template method

    @Override
    protected final IPrincipalService getAPI() {
        return principalService;
    }

    @Override
    protected final Principal createNewEntity() {
        return FixtureFactory.createNewPrincipal();
    }

    @Override
    protected final IEntityOperations<Principal> getEntityOperations() {
        return entityOps;
    }

}
