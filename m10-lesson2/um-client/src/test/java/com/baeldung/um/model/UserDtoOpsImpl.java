package com.baeldung.um.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.springframework.stereotype.Component;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.FixtureResourceFactory;
import com.baeldung.um.persistence.model.User;

@Component
public final class UserDtoOpsImpl implements IDtoOperations<User> {

    public UserDtoOpsImpl() {
        super();
    }

    // API

    // template method

    @Override
    public final User createNewResource() {
        return FixtureResourceFactory.createNewUser();
    }

    @Override
    public final void invalidate(final User entity) {
        entity.setName(null);
    }

    @Override
    public final void change(final User resource) {
        resource.setName(randomAlphabetic(8));
    }

}
