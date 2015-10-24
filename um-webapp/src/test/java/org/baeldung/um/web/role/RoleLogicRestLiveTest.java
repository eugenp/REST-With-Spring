package org.baeldung.um.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.baeldung.client.IDtoOperations;
import org.baeldung.test.common.test.contract.IResourceWithAssociationsIntegrationTest;
import org.baeldung.um.client.template.PrivilegeRestClient;
import org.baeldung.um.client.template.RoleRestClient;
import org.baeldung.um.model.PrivilegeDtoOpsImpl;
import org.baeldung.um.model.RoleDtoOpsImpl;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.test.live.UmLogicRestLiveTest;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;

public class RoleLogicRestLiveTest extends UmLogicRestLiveTest<Role>implements IResourceWithAssociationsIntegrationTest {

    @Autowired
    private RoleRestClient api;
    @Autowired
    private PrivilegeRestClient associationApi;

    @Autowired
    private RoleDtoOpsImpl entityOps;
    @Autowired
    private PrivilegeDtoOpsImpl associationEntityOps;

    public RoleLogicRestLiveTest() {
        super(Role.class);
    }

    // tests

    @Override
    @Test
    public final void givenResourceHasAssociations_whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved() {
        // Given
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final Role newResource = getEntityOps().createNewResource();
        newResource.getPrivileges().add(existingAssociation);

        // When
        final Role existingResource = getApi().create(newResource);

        // Then
        assertThat(existingResource.getPrivileges(), notNullValue());
        assertThat(existingResource.getPrivileges(), not(Matchers.<Privilege> empty()));
    }

    // escaping characters

    @Test
    @Ignore("temp + move in parent")
    public final void givenWorkingWithSpecialCharacters_whtnResourcesIfRetrievedByName_thenResourceIsCorrectlyRetrieved() {
        final Role newResource = getEntityOps().createNewResource();
        newResource.setName("Macy's,Dell, Inc.");
        getApi().createAsResponse(newResource);

        // When
        final Role retrievedResource = getApi().findByName(newResource.getName());
        assertEquals(newResource, retrievedResource);
    }

    // find one

    @Test
    public final void givenResourceExists_whenResourceIsRetrievedByName_thenResourceIsCorrectlyRetrieved() {
        final Role newResource = getEntityOps().createNewResource();
        getApi().create(newResource);
        final Role existingResourceByName = getApi().findByName(newResource.getName());
        assertEquals(newResource, existingResourceByName);
    }

    // find all

    @Test
    @Ignore("in progress - create association first")
    public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved() {
        final Role existingResource = getApi().create(getEntityOps().createNewResource());
        assertThat(existingResource.getPrivileges(), not(Matchers.<Privilege> empty()));
    }

    // create

    @Test
    public final void givenResourceHasNameWithSpace_whenResourceIsCreated_then201IsReceived() {
        final Role newResource = getEntityOps().createNewResource();
        newResource.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));

        // When
        final Response createAsResponse = getApi().createAsResponse(newResource);

        // Then
        assertThat(createAsResponse.getStatusCode(), is(201));
    }

    @Test
    @Ignore("temp")
    public final void givenExistingResourceHasNameWithSpace_whenResourcesIfRetrievedByName_thenResourceIsCorrectlyRetrieved() {
        final Role newResource = getEntityOps().createNewResource();
        newResource.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));
        getApi().createAsResponse(newResource);

        // When
        final Role retrievedResource = getApi().findByName(newResource.getName());
        assertEquals(newResource, retrievedResource);
    }

    @Test
    public final void whenCreatingNewResourceWithExistingAssociations_thenNewResourceIsCorrectlyCreated() {
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final Role newResource = getEntityOps().createNewResource();
        newResource.getPrivileges().add(existingAssociation);
        getApi().create(newResource);

        final Role newResource2 = getEntityOps().createNewResource();
        newResource2.getPrivileges().add(existingAssociation);
        getApi().create(newResource2);
    }

    /**
     * - note: this test ensures that a new User cannot automatically create new Privileges <br>
     * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
     */
    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final Role newResource = getEntityOps().createNewResource();
        newResource.getPrivileges().add(getAssociationEntityOps().createNewResource());

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived() {
        final Privilege invalidAssociation = getAssociationEntityOps().createNewResource();
        getAssociationEntityOps().invalidate(invalidAssociation);
        final Role newResource = getEntityOps().createNewResource();
        newResource.getPrivileges().add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceIsCreatedWithExistingAssociation_then201IsReceived() {
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final Role newResource = getEntityOps().createNewResource();
        newResource.getPrivileges().add(existingAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public final void whenResourceIsCreatedWithExistingAssociation_thenAssociationIsLinkedToTheResource() {
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final Role resourceToCreate = getEntityOps().createNewResource();

        // When
        resourceToCreate.getPrivileges().add(existingAssociation);
        final Role existingResource = getApi().create(resourceToCreate);

        // Then
        assertThat(existingResource.getPrivileges(), hasItem(existingAssociation));
    }

    // update

    @Test
    public final void givenResourceExists_whenResourceIsUpdatedWithExistingAssociation_thenAssociationIsCorrectlyUpdated() {
        // Given
        final Role existingResource = getApi().create(getEntityOps().createNewResource());
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        existingResource.setPrivileges(Sets.newHashSet(existingAssociation));

        // When
        getApi().update(existingResource);

        // Given
        final Role updatedResource = getApi().findOne(existingResource.getId());
        assertThat(updatedResource.getPrivileges(), hasItem(existingAssociation));
    }

    @Test
    public final void givenExistingResourceAndExistingAssociation_whenUpdatingResourceWithTheAssociation_thanAssociationCorrectlyDone() {
        final Role existingResource = getApi().create(getEntityOps().createNewResource());
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        existingResource.setPrivileges(Sets.newHashSet(existingAssociation));

        getApi().update(existingResource);
        final Role updatedResource = getApi().findOne(existingResource.getId());
        assertThat(updatedResource.getPrivileges(), hasItem(existingAssociation));
    }

    // delete

    @Test
    @Ignore("TODO: fix")
    public final void givenResourceWithAssociationsExists_thenResourceCanBeDeleted() {
        // Given
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final Role newResource = getEntityOps().createNewResource();
        newResource.getPrivileges().add(existingAssociation);
        final Role existingResource = getApi().create(newResource);

        // When
        getApi().delete(existingResource.getId());

        // Then
        assertNull(getApi().findOne(existingAssociation.getId()));
    }

    // complex scenarios

    @Test
    public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted() {
        final Privilege existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final Role resource1 = new Role(randomAlphabetic(6), Sets.newHashSet(existingAssociation));

        final Role resource1ViewOfServerBefore = getApi().create(resource1);
        assertThat(resource1ViewOfServerBefore.getPrivileges(), hasItem(existingAssociation));

        final Role resource2 = new Role(randomAlphabetic(6), Sets.newHashSet(existingAssociation));
        getApi().create(resource2);

        final Role resource1ViewOfServerAfter = getApi().findOne(resource1ViewOfServerBefore.getId());
        assertThat(resource1ViewOfServerAfter.getPrivileges(), hasItem(existingAssociation));
    }

    // template

    @Override
    protected final RoleRestClient getApi() {
        return api;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

    // util

    final PrivilegeRestClient getAssociationAPI() {
        return associationApi;
    }

    final IDtoOperations<Privilege> getAssociationEntityOps() {
        return associationEntityOps;
    }

}
