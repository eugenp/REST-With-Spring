package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.persistence.service.IService;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecServicePersistenceIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class PrivilegeServicePersistenceIntegrationTest extends SecServicePersistenceIntegrationTest<Privilege> {

    @Autowired
    private IPrivilegeService privilegeService;

    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

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

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

    // util

    protected final Privilege createNewEntity(final String name) {
        return new Privilege(name);
    }

}
