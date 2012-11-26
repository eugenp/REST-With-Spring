package org.rest.sec.web.user;

import org.rest.sec.client.template.UserTestRestTemplate;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserReadOnlyLogicRestLiveTest extends SecReadOnlyLogicRestLiveTest<User> {

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
