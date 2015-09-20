package org.baeldung.um.web.user;

import org.baeldung.um.client.template.UserTestRestTemplate;
import org.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

public class UserReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<UserDto> {

    @Autowired
    private UserTestRestTemplate api;

    public UserReadOnlyLogicRestLiveTest() {
        super(UserDto.class);
    }

    // tests

    // template method

    @Override
    protected final UserTestRestTemplate getApi() {
        return api;
    }

}
