package org.baeldung.rest.client;

import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.test.live.SecReadOnlyLogicClientRestLiveTest;
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
