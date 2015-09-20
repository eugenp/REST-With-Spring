package org.baeldung.um.web.privilege;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.PrivilegeTestRestTemplate;
import org.baeldung.um.model.PrivilegeDtoOpsImpl;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.test.live.UmLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeLogicRestLiveTest extends UmLogicRestLiveTest<Privilege> {

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
