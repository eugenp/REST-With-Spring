package org.rest.sec.persistence.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecPersistenceDAOIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class PrivilegeDAOPersistenceIntegrationTest extends SecPersistenceDAOIntegrationTest<Privilege> {

    @Autowired
    private IPrivilegeJpaDAO privilegeDao;
    @Autowired
    IRoleJpaDAO associationDao;
    @Autowired
    IPrincipalJpaDAO principalDao;

    // save

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        getAPI().save(createNewEntity());
    }

    // find by

    @Test
    public void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound() {
        // Given
        final String name = randomAlphabetic(8);

        // When
        final Privilege entityByName = getDAOCasted().findByName(name);

        // Then
        assertNull(entityByName);
    }

    // template method

    @Override
    protected final JpaRepository<Privilege, Long> getAPI() {
        return privilegeDao;
    }

    @Override
    protected final Privilege createNewEntity() {
        return new Privilege(randomAlphabetic(8));
    }

    @Override
    protected final void invalidate(final Privilege entity) {
        entity.setName(null);
    }

    @Override
    protected final void changeEntity(final Privilege entity) {
        entity.setName(randomAlphabetic(6));
    }

    //

    protected final IPrivilegeJpaDAO getDAOCasted() {
        return privilegeDao;
    }

}
