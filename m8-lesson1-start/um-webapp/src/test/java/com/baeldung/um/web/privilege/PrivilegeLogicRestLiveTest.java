package com.baeldung.um.web.privilege;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.template.PrivilegeRestClient;
import com.baeldung.um.model.PrivilegeDtoOpsImpl;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.test.live.UmLogicRestLiveTest;

public class PrivilegeLogicRestLiveTest extends UmLogicRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient api;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template

    @Override
    protected final PrivilegeRestClient getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
