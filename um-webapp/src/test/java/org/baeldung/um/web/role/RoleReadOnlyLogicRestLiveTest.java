package org.baeldung.um.web.role;

import org.baeldung.um.client.template.PrivilegeRestClient;
import org.baeldung.um.client.template.RoleRestClient;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<Role> {

    @Autowired
    private RoleRestClient api;
    @Autowired
    private PrivilegeRestClient associationApi;

    public RoleReadOnlyLogicRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template

    @Override
    protected final RoleRestClient getApi() {
        return api;
    }

    // util

    final PrivilegeRestClient getAssociationAPI() {
        return associationApi;
    }

}
