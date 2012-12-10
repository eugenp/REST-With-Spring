package org.rest.sec.client.template.test;

import org.rest.sec.client.template.RoleClientRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecReadOnlyLogicClientRestLiveTest;
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
