package org.baeldung.um.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.common.persistence.service.IService;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.service.IPrivilegeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class PrivilegeServiceIntegrationTest extends SecServiceIntegrationTest<Privilege> {

    @Autowired
    private IPrivilegeService privilegeService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        privilegeService.create(createNewEntity());
    }

    @Test(expected = DataAccessException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
        final String name = randomAlphabetic(8);

        privilegeService.create(createNewEntity(name));
        privilegeService.create(createNewEntity(name));
    }

    // template method

    @Override
    protected final IService<Privilege> getApi() {
        return privilegeService;
    }

    @Override
    protected final Privilege createNewEntity() {
        return new Privilege(randomAlphabetic(8));
    }

    // util

    protected final Privilege createNewEntity(final String name) {
        return new Privilege(name);
    }

    @Override
    protected void invalidate(final Privilege entity) {
        entity.setName(null);
    }

    @Override
    protected void change(final Privilege entity) {
        entity.setName(randomAlphabetic(6));
    }

}
