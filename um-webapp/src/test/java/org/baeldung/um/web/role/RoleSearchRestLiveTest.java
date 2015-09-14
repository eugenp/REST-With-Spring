package org.baeldung.um.web.role;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.RoleTestRestTemplate;
import org.baeldung.um.model.Role;
import org.baeldung.um.model.RoleDtoOpsImpl;
import org.baeldung.um.test.live.UmSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchRestLiveTest extends UmSearchRestLiveTest<Role> {

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
