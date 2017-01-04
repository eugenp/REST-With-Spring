package com.baeldung.um.web.privilege;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.template.PrivilegeTestRestTemplate;
import com.baeldung.um.model.PrivilegeDtoOpsImpl;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.test.live.UmLogicRestLiveTest;

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
