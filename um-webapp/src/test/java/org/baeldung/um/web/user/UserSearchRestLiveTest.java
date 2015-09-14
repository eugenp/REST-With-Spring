package org.baeldung.um.web.user;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.UserTestRestTemplate;
import org.baeldung.um.model.User;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.test.live.UmSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSearchRestLiveTest extends UmSearchRestLiveTest<User> {

    @Autowired
    private UserTestRestTemplate restTemplate;
    @Autowired
    private UserDtoOpsImpl entityOps;

    public UserSearchRestLiveTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final UserTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

}
