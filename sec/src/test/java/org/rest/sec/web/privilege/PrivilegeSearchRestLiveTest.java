package org.rest.sec.web.privilege;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeTestRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchRestLiveTest extends SecSearchRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate restTemplate;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeSearchRestLiveTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
