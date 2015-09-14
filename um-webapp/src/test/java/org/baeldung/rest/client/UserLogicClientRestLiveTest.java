package org.baeldung.rest.client;

import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.User;
import org.baeldung.rest.model.UserDtoOpsImpl;
import org.baeldung.rest.test.live.SecLogicClientRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserLogicClientRestLiveTest extends SecLogicClientRestLiveTest<User> {

    @Autowired
    private UserClientRestTemplate api;
    @Autowired
    private UserDtoOpsImpl entityOps;

    public UserLogicClientRestLiveTest() {
        super();
    }

    // tests

    // template method

    @Override
    protected final UserClientRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

}
