package org.baeldung.um.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import org.baeldung.common.persistence.service.IService;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.service.IPrincipalService;
import org.baeldung.um.service.IPrivilegeService;
import org.baeldung.um.service.IRoleService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.google.common.collect.Sets;

public class RoleServiceIntegrationTest extends SecServiceIntegrationTest<Role> {

    @Autowired
    private IPrivilegeService privilegeService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPrincipalService principalService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        roleService.create(createNewEntity());
    }

    @Test(expected = DataAccessException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
        final String name = randomAlphabetic(8);

        roleService.create(createNewEntity(name));
        roleService.create(createNewEntity(name));
    }

    // scenario

    /** - known issue: this fails on a H2 database */
    @Test
    @Ignore
    public final void givenEntityExistsWithAssociationScenarios_whenDeletingEverything_thenNoException() {
        final Privilege existingAssociation = getAssociationService().create(new Privilege(randomAlphabetic(6)));
        final Role newResource = createNewEntity();
        newResource.getPrivileges().add(existingAssociation);
        getApi().create(newResource);

        principalService.deleteAll();
        roleService.deleteAll();
        // privilegeService.deleteAll();
    }

    @Test
    public final void whenCreatingNewResourceWithExistingAssociations_thenNewResourceIsCorrectlyCreated() {
        final Privilege existingAssociation = getAssociationService().create(new Privilege(randomAlphabetic(6)));
        final Role newResource = createNewEntity();
        newResource.getPrivileges().add(existingAssociation);
        getApi().create(newResource);

        final Role newResource2 = createNewEntity();
        newResource2.getPrivileges().add(existingAssociation);
        getApi().create(newResource2);
    }

    @Test
    public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted() {
        final Privilege existingAssociation = getAssociationService().create(new Privilege(randomAlphabetic(6)));
        final Role resource1 = new Role(randomAlphabetic(6), Sets.newHashSet(existingAssociation));

        final Role resource1ViewOfServerBefore = getApi().create(resource1);
        assertThat(resource1ViewOfServerBefore.getPrivileges(), hasItem(existingAssociation));

        final Role resource2 = new Role(randomAlphabetic(6), Sets.newHashSet(existingAssociation));
        getApi().create(resource2);

        final Role resource1ViewOfServerAfter = getApi().findOne(resource1ViewOfServerBefore.getId());
        assertThat(resource1ViewOfServerAfter.getPrivileges(), hasItem(existingAssociation));
    }

    // template method

    @Override
    protected final IService<Role> getApi() {
        return roleService;
    }

    @Override
    protected final Role createNewEntity() {
        return createNewEntity(randomAlphabetic(8));
    }

    @Override
    protected void invalidate(final Role entity) {
        entity.setName(null);
    }

    @Override
    protected void change(final Role entity) {
        entity.setName(randomAlphabetic(6));
    }

    final IPrivilegeService getAssociationService() {
        return privilegeService;
    }

    protected final Role createNewEntity(final String name) {
        return new Role(name, Sets.<Privilege> newHashSet());
    }

}
