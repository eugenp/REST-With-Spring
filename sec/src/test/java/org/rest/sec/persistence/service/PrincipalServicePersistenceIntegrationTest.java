package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.common.persistence.service.IService;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecPersistenceServiceIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.google.common.collect.Sets;

public class PrincipalServicePersistenceIntegrationTest extends SecPersistenceServiceIntegrationTest<Principal> {

    @Autowired
    private IPrincipalService principalService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        getAPI().create(createNewEntity());
    }

    @Test(expected = DataAccessException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
        final String name = randomAlphabetic(8);

        getAPI().create(createNewEntity(name));
        getAPI().create(createNewEntity(name));
    }

    // template method

    @Override
    protected final IService<Principal> getAPI() {
        return principalService;
    }

    @Override
    protected final Principal createNewEntity() {
        return new Principal(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    @Override
    protected final void invalidate(final Principal entity) {
        entity.setName(null);
    }

    @Override
    protected final void changeEntity(final Principal entity) {
        entity.setPassword(randomAlphabetic(8));
    }

    //

    protected final Principal createNewEntity(final String name) {
        return new Principal(name, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

}
