package org.rest.sec.client.template.test;

import org.rest.sec.client.template.PrivilegeClientRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecReadOnlyLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeReadOnlyLogicClientRestLiveTest extends SecReadOnlyLogicClientRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeClientRestTemplate api;

    public PrivilegeReadOnlyLogicClientRestLiveTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final PrivilegeClientRestTemplate getApi() {
        return api;
    }

}
