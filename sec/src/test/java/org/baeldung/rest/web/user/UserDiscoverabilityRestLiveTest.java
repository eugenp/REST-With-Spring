package org.baeldung.rest.web.user;

import org.baeldung.rest.client.template.UserTestRestTemplate;
import org.baeldung.rest.common.client.IDtoOperations;
import org.baeldung.rest.model.User;
import org.baeldung.rest.model.UserDtoOpsImpl;
import org.baeldung.rest.test.live.SecDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDiscoverabilityRestLiveTest extends SecDiscoverabilityRestLiveTest<User> {

    @Autowired
    private UserTestRestTemplate restTemplate;
    @Autowired
    private UserDtoOpsImpl entityOps;

    public UserDiscoverabilityRestLiveTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final User createNewEntity() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final UserTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

}
