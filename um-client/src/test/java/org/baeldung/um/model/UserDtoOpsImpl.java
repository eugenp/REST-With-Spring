package org.baeldung.um.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.model.Role;
import org.baeldung.um.model.User;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public final class UserDtoOpsImpl implements IDtoOperations<User> {

    public UserDtoOpsImpl() {
        super();
    }

    // API

    public final User createNewEntity(final String name) {
        return new User(name, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    // template method

    @Override
    public final User createNewResource() {
        return new User(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
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
