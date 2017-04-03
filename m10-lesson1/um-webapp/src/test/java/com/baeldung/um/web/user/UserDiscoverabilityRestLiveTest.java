package com.baeldung.um.web.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.template.UserRestClient;
import com.baeldung.um.model.UserDtoOpsImpl;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;

public class UserDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<User> {

    @Autowired
    private UserRestClient restTemplate;
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
    protected final User createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final UserRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

}
