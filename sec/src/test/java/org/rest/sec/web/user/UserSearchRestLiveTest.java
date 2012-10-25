package org.rest.sec.web.user;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.UserTestRestTemplate;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecSearchRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSearchRestLiveTest extends SecSearchRestLiveTest<User> {

    @Autowired
    private UserTestRestTemplate restTemplate;
    @Autowired
    private UserEntityOpsImpl entityOps;

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
    protected final IEntityOperations<User> getEntityOps() {
        return entityOps;
    }

}
