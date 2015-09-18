package org.baeldung.um.web.privilege;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.PrivilegeTestRestTemplate;
import org.baeldung.um.model.Privilege;
import org.baeldung.um.model.PrivilegeDtoOpsImpl;
import org.baeldung.um.test.live.UmSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchRestLiveTest extends UmSearchRestLiveTest<Privilege> {

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
