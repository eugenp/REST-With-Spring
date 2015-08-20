package org.baeldung.rest.web.user;

import org.baeldung.rest.client.template.UserTestRestTemplate;
import org.baeldung.rest.model.User;
import org.baeldung.rest.test.live.SecReadOnlyLogicRestLiveTest;
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
