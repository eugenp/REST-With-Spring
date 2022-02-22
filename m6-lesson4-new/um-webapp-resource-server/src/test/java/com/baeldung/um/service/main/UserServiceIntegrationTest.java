package com.baeldung.um.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.baeldung.common.persistence.service.IService;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.service.IUserService;
import com.google.common.collect.Sets;

public class UserServiceIntegrationTest extends SecServiceIntegrationTest<User> {

    @Autowired
    private IUserService userService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        getApi().create(createNewEntity());
    }

    @Test(expected = DataAccessException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
        final String name = randomAlphabetic(8);

        getApi().create(createNewEntity(name));
        getApi().create(createNewEntity(name));
    }

    // template method

    @Override
    protected final IService<User> getApi() {
        return userService;
    }

    @Override
    protected final User createNewEntity() {
        return new User(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    protected final User createNewEntity(final String name) {
        return new User(name, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    @Override
    protected void invalidate(final User entity) {
        entity.setName(null);
    }

    @Override
    protected void change(final User entity) {
        entity.setName(randomAlphabetic(6));
    }

}
