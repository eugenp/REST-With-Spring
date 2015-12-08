package org.baeldung.um.web.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.FixtureResourceFactory;
import org.baeldung.um.client.template.RoleRestClient;
import org.baeldung.um.client.template.UserRestClient;
import org.baeldung.um.model.RoleDtoOpsImpl;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.test.live.UmLogicRestLiveTest;
import org.baeldung.um.web.dto.UserDto;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;

public class UserLogicRestLiveTest extends UmLogicRestLiveTest<UserDto> {

    @Autowired
    private UserRestClient api;
    @Autowired
    private RoleRestClient associationApi;

    @Autowired
    private UserDtoOpsImpl entityOps;
    @Autowired
    private RoleDtoOpsImpl associationOps;

    public UserLogicRestLiveTest() {
        super(UserDto.class);
    }

    // tests

    // find - one

    @Test
    @Ignore("in progress - create association first")
    public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved() {
        final UserDto existingResource = getApi().create(getEntityOps().createNewResource());
        assertThat(existingResource.getRoles(), not(Matchers.<Role> empty()));
    }

    // create

    /**
     * - note: this test ensures that a new User cannot automatically create new Privileges <br>
     * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
     */
    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final UserDto newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(getAssociationEntityOps().createNewResource());

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    @Ignore("intermitent failures - temporarily ignored")
    public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived() {
        final Role invalidAssociation = getAssociationEntityOps().createNewResource();
        invalidAssociation.setId(1001l);
        final UserDto newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenUserIsCreatedWithExistingRole_then201IsReceived() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final UserDto newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(existingAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    // TODO: sort

    @Test
    public final void whenScenario_getResource_getAssociationsById() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final UserDto resourceToCreate = getEntityOps().createNewResource();
        resourceToCreate.getRoles().add(existingAssociation);

        // When
        final UserDto existingResource = getApi().create(resourceToCreate);
        for (final Role associationOfResourcePotential : existingResource.getRoles()) {
            final Role existingAssociationOfResource = getAssociationAPI().findOne(associationOfResourcePotential.getId());
            assertThat(existingAssociationOfResource, notNullValue());
        }
    }

    // scenarios

    @Test
    public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted() {
        final Role child = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final UserDto parent = FixtureResourceFactory.createNewUser();
        parent.setRoles(Sets.newHashSet(child));
        final UserDto parentWithChild = getApi().create(parent);
        assertThat(parentWithChild.getRoles(), hasItem(child));

        final UserDto parent2 = FixtureResourceFactory.createNewUser();
        parent2.setRoles(Sets.newHashSet(child));
        getApi().createAsResponse(parent2);

        final UserDto resource1ViewOfServerAfter = getApi().findOne(parentWithChild.getId());
        assertThat(resource1ViewOfServerAfter.getRoles(), hasItem(child));
    }

    // template method

    @Override
    protected final UserRestClient getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<UserDto> getEntityOps() {
        return entityOps;
    }

    final RoleRestClient getAssociationAPI() {
        return associationApi;
    }

    final IDtoOperations<Role> getAssociationEntityOps() {
        return associationOps;
    }

}
