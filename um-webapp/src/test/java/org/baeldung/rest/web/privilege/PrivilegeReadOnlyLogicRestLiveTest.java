package org.baeldung.rest.web.privilege;

import org.baeldung.rest.client.template.PrivilegeTestRestTemplate;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.test.live.SecReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeReadOnlyLogicRestLiveTest extends SecReadOnlyLogicRestLiveTest<Privilege> {

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
