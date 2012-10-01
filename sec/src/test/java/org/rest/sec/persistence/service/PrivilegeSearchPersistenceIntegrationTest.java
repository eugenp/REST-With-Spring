package org.rest.sec.persistence.service;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecSearchPersistenceIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchPersistenceIntegrationTest extends SecSearchPersistenceIntegrationTest<Privilege> {

    @Autowired
    private IPrivilegeService privilegeService;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    // tests

    // template method

    @Override
    protected final IPrivilegeService getApi() {
        return privilegeService;
    }

    @Override
    protected final Privilege createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
