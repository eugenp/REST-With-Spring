package org.baeldung.rest.client;

import org.baeldung.rest.model.Role;
import org.baeldung.rest.test.live.SecReadOnlyLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleReadOnlyLogicClientRestLiveTest extends SecReadOnlyLogicClientRestLiveTest<Role> {

    @Autowired
    private RoleClientRestTemplate api;

    public RoleReadOnlyLogicClientRestLiveTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final RoleClientRestTemplate getApi() {
        return api;
    }

}
