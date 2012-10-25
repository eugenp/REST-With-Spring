package org.rest.sec.web.role;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.RoleTestRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.test.SecSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchRestLiveTest extends SecSearchRestLiveTest<Role> {

    @Autowired
    private RoleTestRestTemplate restTemplate;
    @Autowired
    private RoleEntityOpsImpl entityOps;

    public RoleSearchRestLiveTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final RoleTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Role> getEntityOps() {
        return entityOps;
    }

}
