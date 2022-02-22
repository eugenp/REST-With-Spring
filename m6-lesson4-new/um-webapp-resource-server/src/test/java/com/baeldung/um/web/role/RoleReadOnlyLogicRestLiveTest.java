package com.baeldung.um.web.role;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.um.client.template.PrivilegeTestRestTemplate;
import com.baeldung.um.client.template.RoleTestRestTemplate;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;

public class RoleReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<Role> {

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
