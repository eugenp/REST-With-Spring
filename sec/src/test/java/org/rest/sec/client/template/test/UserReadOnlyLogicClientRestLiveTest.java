package org.rest.sec.client.template.test;

import org.rest.sec.client.template.UserClientRestTemplate;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecReadOnlyLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserReadOnlyLogicClientRestLiveTest extends SecReadOnlyLogicClientRestLiveTest<User> {

    @Autowired
    private UserClientRestTemplate api;

    public UserReadOnlyLogicClientRestLiveTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserClientRestTemplate getApi() {
        return api;
    }

}
