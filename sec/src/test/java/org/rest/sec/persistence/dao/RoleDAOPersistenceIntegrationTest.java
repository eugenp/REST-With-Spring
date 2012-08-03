package org.rest.sec.persistence.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceDAOIntegrationTest;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RoleDAOPersistenceIntegrationTest extends AbstractPersistenceDAOIntegrationTest<Role> {

    @Autowired IPrivilegeJpaDAO privilegeDao;
    @Autowired private IRoleJpaDAO roleDao;
    @Autowired IPrincipalJpaDAO principalDao;

    // find by

    @Test
    public void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound() {
        // Given
        final String name = randomAlphabetic(8);

        // When
        final Role entityByName = getDAOCasted().findByName(name);

        // Then
        assertNull(entityByName);
    }

    // template method

    @Override
    protected final JpaRepository<Role, Long> getAPI() {
        return roleDao;
    }

    @Override
    protected final Role createNewEntity() {
        final Role entity = new Role(randomAlphabetic(8));
        entity.setPrivileges(Sets.<Privilege> newHashSet());
        return entity;
    }

    @Override
    protected final void invalidate(final Role entity) {
        entity.setName(null);
    }

    @Override
    protected final void changeEntity(final Role entity) {
        entity.setName(randomAlphabetic(6));
    }

    //

    protected final IRoleJpaDAO getDAOCasted() {
        return roleDao;
    }

}
