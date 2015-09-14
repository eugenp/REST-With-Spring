package org.baeldung.rest.web.privilege;

import org.baeldung.rest.client.template.PrivilegeTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.model.PrivilegeDtoOpsImpl;
import org.baeldung.rest.test.live.SecSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchRestLiveTest extends SecSearchRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate restTemplate;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

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
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
