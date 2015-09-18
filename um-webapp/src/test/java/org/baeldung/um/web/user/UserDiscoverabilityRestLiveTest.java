package org.baeldung.um.web.user;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.UserTestRestTemplate;
import org.baeldung.um.model.User;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<User> {

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
