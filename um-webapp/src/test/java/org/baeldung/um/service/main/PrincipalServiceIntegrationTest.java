package org.baeldung.um.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.common.persistence.service.IService;
import org.baeldung.um.persistence.model.Principal;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.service.IPrincipalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.google.common.collect.Sets;

public class PrincipalServiceIntegrationTest extends SecServiceIntegrationTest<Principal> {

    @Autowired
    private IPrincipalService principalService;

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
    protected final IService<Principal> getApi() {
        return principalService;
    }

    @Override
    protected final Principal createNewEntity() {
        return new Principal(randomAlphabetic(8), randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    protected final Principal createNewEntity(final String name) {
        return new Principal(name, randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    @Override
    protected void invalidate(final Principal entity) {
        entity.setName(null);
    }

    @Override
    protected void change(final Principal entity) {
        entity.setName(randomAlphabetic(6));
    }

}
