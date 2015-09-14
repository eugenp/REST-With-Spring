package org.baeldung.um.web.privilege;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.PrivilegeTestRestTemplate;
import org.baeldung.um.model.Privilege;
import org.baeldung.um.model.PrivilegeDtoOpsImpl;
import org.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate restTemplate;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeDiscoverabilityRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template method

    @Override
    protected final Privilege createNewEntity() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
