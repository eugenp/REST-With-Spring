package org.baeldung.um.web.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.template.RoleTestRestTemplate;
import org.baeldung.um.client.template.UserTestRestTemplate;
import org.baeldung.um.model.Role;
import org.baeldung.um.model.RoleDtoOpsImpl;
import org.baeldung.um.model.User;
import org.baeldung.um.model.UserDtoOpsImpl;
import org.baeldung.um.test.live.UmLogicRestLiveTest;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;

public class UserLogicRestLiveTest extends UmLogicRestLiveTest<User> {

    @Autowired
    private UserTestRestTemplate api;
    @Autowired
    private RoleTestRestTemplate associationApi;

    @Autowired
    private UserDtoOpsImpl entityOps;
    @Autowired
    private RoleDtoOpsImpl associationOps;

    public UserLogicRestLiveTest() {
        super(User.class);
    }

    // tests

    // find - one

    @Test
    @Ignore("in progress - create association first")
    public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved() {
        final User existingResource = getApi().create(getEntityOps().createNewResource());
        assertThat(existingResource.getRoles(), not(Matchers.<Role> empty()));
    }

    // create

    /**
     * - note: this test ensures that a new User cannot automatically create new Privileges <br>
     * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
     */
    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final User newResource = getEntityOps().createNewResource();
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
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenUserIsCreatedWithExistingRole_then201IsReceived() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User newResource = getEntityOps().createNewResource();
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
        final User resourceToCreate = getEntityOps().createNewResource();
        resourceToCreate.getRoles().add(existingAssociation);

        // When
        final User existingResource = getApi().create(resourceToCreate);
        for (final Role associationOfResourcePotential : existingResource.getRoles()) {
            final Role existingAssociationOfResource = getAssociationAPI().findOne(associationOfResourcePotential.getId());
            assertThat(existingAssociationOfResource, notNullValue());
        }
    }

    // scenarios

    @Test
    public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User resource1 = new User(randomAlphabetic(6), randomAlphabetic(6), Sets.newHashSet(existingAssociation));

        final User resource1ViewOfServerBefore = getApi().create(resource1);
        assertThat(resource1ViewOfServerBefore.getRoles(), hasItem(existingAssociation));

        final User resource2 = new User(randomAlphabetic(6), randomAlphabetic(6), Sets.newHashSet(existingAssociation));
        getApi().createAsResponse(resource2);

        final User resource1ViewOfServerAfter = getApi().findOne(resource1ViewOfServerBefore.getId());
        assertThat(resource1ViewOfServerAfter.getRoles(), hasItem(existingAssociation));
    }

    // template method

    @Override
    protected final UserTestRestTemplate getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

    final RoleTestRestTemplate getAssociationAPI() {
        return associationApi;
    }

    final IDtoOperations<Role> getAssociationEntityOps() {
        return associationOps;
    }

}
