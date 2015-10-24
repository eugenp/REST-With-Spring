package org.baeldung.um.web.privilege;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.PrivilegeRestClient;
import org.baeldung.um.model.PrivilegeDtoOpsImpl;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.test.live.UmSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeSearchRestLiveTest extends UmSearchRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient restTemplate;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeSearchRestLiveTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final PrivilegeRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
