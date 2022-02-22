package com.baeldung.um.web.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.um.client.template.UserTestRestTemplate;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;

public class UserReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<User> {

    @Autowired
    private UserTestRestTemplate api;

    public UserReadOnlyLogicRestLiveTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final UserTestRestTemplate getApi() {
        return api;
    }

}
