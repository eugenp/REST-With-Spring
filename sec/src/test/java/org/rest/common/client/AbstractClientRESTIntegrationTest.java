package org.rest.common.client;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.template.IClientTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.util.IDUtils;

public abstract class AbstractClientRESTIntegrationTest<T extends INameableEntity> {

    private static final String NAME_KEY = "name";
    private static final String NAGATED_NAME_KEY = "name~";

    public AbstractClientRESTIntegrationTest() {
        super();
    }

    // @Rule public ExpectedException thrown = ExpectedException.none();

    // tests

    // find one

    @Test
    public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        // Given
        final T newResource = getEntityOps().createNewEntity();
        final String uriOfExistingResource = getAPI().createAsURI(newResource);

        // When
        final T createdResource = getAPI().findOneByURI(uriOfExistingResource);

        // Then
        assertEquals(createdResource, newResource);
    }

    @Test
    public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        final T createdResource = getAPI().findOne(IDUtils.randomPositiveLong());

        // Then
        assertNull(createdResource);
    }

    // find one - by name

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByName_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        getAPI().findOneByName(existingResource.getName());
    }

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByName_thenResourceIsFound() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByName(existingResource.getName());

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByName_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByName(existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    // find one by attributes

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        getAPI().findOneByAttributes(NAME_KEY, existingResource.getName());
    }

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenResourceIsFound() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByAttributes(NAME_KEY, existingResource.getName());

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByAttributes(NAME_KEY, existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    @Ignore("not yet done")
    public final void givenResourceExists_whenResourceIsSearchedByNagatedNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(getEntityOps().createNewEntity());

        // When
        getAPI().findAllByAttributes(NAGATED_NAME_KEY, existingResource.getName());

        // Then
    }

    // find all

    @Test
    public final void whenAllResourcesAreRetrieved_thenResourcesExist() {
        final List<T> allResources = getAPI().findAll();
        assertFalse(allResources.isEmpty());
    }

    // update

    @Test
    public final void whenResourceIsUpdated_thenTheChangesAreCorrectlyPersisted() {
        final T existingResource = getAPI().givenAuthenticated().create(getEntityOps().createNewEntity());

        // When
        getEntityOps().change(existingResource);
        getAPI().givenAuthenticated().update(existingResource);
        final T updatedResource = getAPI().findOne(existingResource.getId());

        // Then
        assertThat(existingResource, equalTo(updatedResource));
    }

    // delete

    @Test
    public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = getAPI().givenAuthenticated().create(getEntityOps().createNewEntity());

        // When
        getAPI().delete(existingResource.getId());

        // Then
        assertNull(getAPI().findOne(existingResource.getId()));
    }

    // template method

    protected abstract IClientTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

}
