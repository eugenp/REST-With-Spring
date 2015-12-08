package org.baeldung.um.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.FixtureResourceFactory;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public final class UserDtoOpsImpl implements IDtoOperations<UserDto> {

    public UserDtoOpsImpl() {
        super();
    }

    // API

    public final UserDto createNewEntity(final String name) {
        return FixtureResourceFactory.createNewUser(name);
    }

    // template method

    @Override
    public final UserDto createNewResource() {
        return FixtureResourceFactory.createNewUser();
    }

    @Override
    public final void invalidate(final UserDto entity) {
        entity.setName(null);
    }

    @Override
    public final void change(final UserDto resource) {
        resource.setName(randomAlphabetic(8));
    }

}
