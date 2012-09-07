package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeClientRESTTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecClientLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeClientLogicRESTIntegrationTest extends SecClientLogicRESTIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeClientRESTTemplate clientTemplate;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeClientLogicRESTIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeClientRESTTemplate getAPI() {
        return clientTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
