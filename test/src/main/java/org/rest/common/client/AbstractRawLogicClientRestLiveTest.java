package org.rest.common.client;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.rest.common.client.template.IRawClientTemplate;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.util.IDUtil;
import org.rest.common.web.WebConstants;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

@ActiveProfiles({ "client", "test", "mime_json" })
public abstract class AbstractRawLogicClientRestLiveTest<T extends IEntity> {

    public AbstractRawLogicClientRestLiveTest() {
        super();
    }

    // tests

    // find - one

    @Test(expected = RestClientException.class)
    public void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenException() {
        getApi().findOneByUri(getUri() + WebConstants.PATH_SEP + randomNumeric(4), null);
    }

    @Test
    public final void givenResourceExists_whenResourceIsRetrieved_thenResourceHasId() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getApi().createAsUri(newResource, null);

        // When
        final T createdResource = getApi().findOneByUri(uriOfExistingResource, null);

        // Then
        assertThat(createdResource.getId(), notNullValue());
    }

    @Test
    public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getApi().createAsUri(newResource, null);

        // When
        final T createdResource = getApi().findOneByUri(uriOfExistingResource, null);

        // Then
        assertEquals(createdResource, newResource);
    }

    @Test
    /**/public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        final T createdResource = getApi().findOne(IDUtil.randomPositiveLong());

        // Then
        assertNull(createdResource);
    }

    // find - all

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenNoExceptions() {
        getApi().findAll();
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenTheResultIsNotNull() {
        final List<T> resources = getApi().findAll();

        assertNotNull(resources);
    }

    @Test
    /**/public void givenAtLeastOneResourceExists_whenAllResourcesAreRetrieved_thenRetrievedResourcesAreNotEmpty() {
        // Given
        ensureOneResourceExists();

        // When
        final List<T> allResources = getApi().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    @Test
    /**/public void givenAnResourceExists_whenAllResourcesAreRetrieved_thenTheExistingResourceIsIndeedAmongThem() {
        final T existingResource = getApi().create(createNewEntity());

        final List<T> resources = getApi().findAll();

        assertThat(resources, hasItem(existingResource));
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenResourcesHaveIds() {
        ensureOneResourceExists();

        // When
        final List<T> allResources = getApi().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // create

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsCreated_thenException() {
        getApi().create(null);
    }

    @Test
    /**/public void whenResourceIsCreated_thenNoExceptions() {
        getApi().createAsUri(createNewEntity(), null);
    }

    @Test
    /**/public void whenResourceIsCreated_thenResourceIsRetrievable() {
        final T existingResource = getApi().create(createNewEntity());

        assertNotNull(getApi().findOne(existingResource.getId()));
    }

    @Test
    /**/public void whenResourceIsCreated_thenSavedResourceIsEqualToOriginalResource() {
        final T originalResource = createNewEntity();
        final T savedResource = getApi().create(originalResource);

        assertEquals(originalResource, savedResource);
    }

    // update

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsUpdated_thenException() {
        getApi().update(null);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().update(existingResource);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getEntityOps().change(existingResource);
        getApi().update(existingResource);

        final T updatedResourceFromServer = getApi().findOne(existingResource.getId());

        // Then
        assertEquals(existingResource, updatedResourceFromServer);
    }

    // delete

    @Test
    public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().delete(existingResource.getId());

        // Then
        assertNull(getApi().findOne(existingResource.getId()));
    }

    // template method

    protected abstract IRawClientTemplate<T> getApi();

    protected abstract IEntityOperations<T> getEntityOps();

    protected final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    protected void ensureOneResourceExists() {
        getApi().createAsUri(createNewEntity(), null);
    }

}
