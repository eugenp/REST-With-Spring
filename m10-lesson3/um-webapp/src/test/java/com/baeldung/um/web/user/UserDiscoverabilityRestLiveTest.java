package com.baeldung.um.web.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.template.UserRestClient;
import com.baeldung.um.model.UserDtoOpsImpl;
import com.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;
import com.baeldung.um.web.dto.UserDto;

public class UserDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<UserDto> {

    @Autowired
    private UserRestClient restTemplate;
    @Autowired
    private UserDtoOpsImpl entityOps;

    public UserDiscoverabilityRestLiveTest() {
        super(UserDto.class);
    }

    // tests

    // template method

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final UserDto createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final UserRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<UserDto> getEntityOps() {
        return entityOps;
    }

}
