package com.baeldung.um.web.role;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.um.client.template.PrivilegeRestClient;
import com.baeldung.um.client.template.RoleRestClient;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;

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
