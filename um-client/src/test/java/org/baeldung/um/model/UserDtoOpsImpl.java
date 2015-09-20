package org.baeldung.um.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public final class UserDtoOpsImpl implements IDtoOperations<UserDto> {

    public UserDtoOpsImpl() {
        super();
    }

    // API

    public final UserDto createNewEntity(final String name) {
        return new UserDto(name, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    // template method

    @Override
    public final UserDto createNewResource() {
        return new UserDto(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
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
