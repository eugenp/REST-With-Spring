package org.baeldung.rest.client;

import org.baeldung.rest.model.User;
import org.baeldung.rest.test.live.SecReadOnlyLogicClientRestLiveTest;
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
