package org.baeldung.um.web.user;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.UserRestClient;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.test.live.UmSearchRestLiveTest;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSearchRestLiveTest extends UmSearchRestLiveTest<UserDto> {

    @Autowired
    private UserRestClient restTemplate;
    @Autowired
    private UserDtoOpsImpl entityOps;

    public UserSearchRestLiveTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final UserRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<UserDto> getEntityOps() {
        return entityOps;
    }

}
