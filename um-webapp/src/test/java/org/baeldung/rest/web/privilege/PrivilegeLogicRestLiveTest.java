package org.baeldung.rest.web.privilege;

import org.baeldung.rest.client.template.PrivilegeTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.model.PrivilegeDtoOpsImpl;
import org.baeldung.rest.test.live.SecLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeLogicRestLiveTest extends SecLogicRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate api;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
