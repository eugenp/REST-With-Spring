package com.baeldung.um.web.privilege;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.um.client.template.PrivilegeTestRestTemplate;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;

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
