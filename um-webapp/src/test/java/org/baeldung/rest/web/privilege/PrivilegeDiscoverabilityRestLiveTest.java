package org.baeldung.rest.web.privilege;

import org.baeldung.rest.client.template.PrivilegeTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.model.PrivilegeDtoOpsImpl;
import org.baeldung.rest.test.live.SecDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeDiscoverabilityRestLiveTest extends SecDiscoverabilityRestLiveTest<Privilege> {

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
