package org.baeldung.rest.web.role;

import org.baeldung.rest.client.template.RoleTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.model.RoleDtoOpsImpl;
import org.baeldung.rest.test.live.SecSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchRestLiveTest extends SecSearchRestLiveTest<Role> {

    @Autowired
    private RoleTestRestTemplate restTemplate;
    @Autowired
    private RoleDtoOpsImpl entityOps;

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
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

}
