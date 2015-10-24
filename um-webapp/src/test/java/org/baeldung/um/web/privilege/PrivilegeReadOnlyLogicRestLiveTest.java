package org.baeldung.um.web.privilege;

import org.baeldung.um.client.template.PrivilegeRestClient;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient api;

    public PrivilegeReadOnlyLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template

    @Override
    protected final PrivilegeRestClient getApi() {
        return api;
    }

}
