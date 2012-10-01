package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeTestRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecMimeRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeMimeRestIntegrationTest extends SecMimeRestIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate api;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeMimeRestIntegrationTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
