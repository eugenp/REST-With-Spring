package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeTestRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecSearchRestIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchRestIntegrationTest extends SecSearchRestIntegrationTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate restTemplate;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeSearchRestIntegrationTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final PrivilegeTestRestTemplate getAPI() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
