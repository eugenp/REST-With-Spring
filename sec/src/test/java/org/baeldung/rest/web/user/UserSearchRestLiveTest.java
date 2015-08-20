package org.baeldung.rest.web.user;

import org.baeldung.rest.client.template.UserTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.User;
import org.baeldung.rest.model.UserDtoOpsImpl;
import org.baeldung.rest.test.live.SecSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSearchRestLiveTest extends SecSearchRestLiveTest<User> {

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
