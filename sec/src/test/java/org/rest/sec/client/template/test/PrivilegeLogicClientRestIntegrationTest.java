package org.rest.sec.client.template.test;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeClientRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecLogicClientRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeLogicClientRestIntegrationTest extends SecLogicClientRestIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeClientRestTemplate api;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeLogicClientRestIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeClientRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
