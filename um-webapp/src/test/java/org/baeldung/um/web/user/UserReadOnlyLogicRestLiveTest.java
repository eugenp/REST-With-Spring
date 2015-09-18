package org.baeldung.um.web.user;

import org.baeldung.um.client.template.UserTestRestTemplate;
import org.baeldung.um.model.User;
import org.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

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
