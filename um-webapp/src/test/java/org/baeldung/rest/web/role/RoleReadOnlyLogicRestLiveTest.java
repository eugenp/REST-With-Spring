package org.baeldung.rest.web.role;

import org.baeldung.rest.client.template.PrivilegeTestRestTemplate;
import org.baeldung.rest.client.template.RoleTestRestTemplate;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.test.live.SecReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleReadOnlyLogicRestLiveTest extends SecReadOnlyLogicRestLiveTest<Role> {

    @Autowired
    private RoleTestRestTemplate api;
    @Autowired
    private PrivilegeTestRestTemplate associationApi;

    public RoleReadOnlyLogicRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template

    @Override
    protected final RoleTestRestTemplate getApi() {
        return api;
    }

    // util

    final PrivilegeTestRestTemplate getAssociationAPI() {
        return associationApi;
    }

}
