package org.baeldung.rest.client;

import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.model.PrivilegeDtoOpsImpl;
import org.baeldung.rest.test.live.SecLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeLogicClientRestLiveTest extends SecLogicClientRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeClientRestTemplate api;

    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeLogicClientRestLiveTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeClientRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
