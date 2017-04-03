package com.baeldung.um.web.role;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.template.RoleRestClient;
import com.baeldung.um.model.RoleDtoOpsImpl;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;

public class RoleDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<Role> {

    @Autowired
    private RoleRestClient restTemplate;

    @Autowired
    private RoleDtoOpsImpl entityOps;

    public RoleDiscoverabilityRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final RoleRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

}
