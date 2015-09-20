package org.baeldung.um.web.privilege;

import org.baeldung.um.client.template.PrivilegeTestRestTemplate;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate api;

    public PrivilegeReadOnlyLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return api;
    }

}
