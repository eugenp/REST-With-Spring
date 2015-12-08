package org.baeldung.um.web.user;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.UserRestClient;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

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
