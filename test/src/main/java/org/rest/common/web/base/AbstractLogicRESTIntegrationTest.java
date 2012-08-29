package org.rest.common.web.base;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.util.IDUtils;
import org.rest.common.util.QueryConstants;
import org.rest.common.util.SearchField;
import org.rest.common.web.WebConstants;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractLogicRESTIntegrationTest<T extends INameableEntity> {

    protected final Class<T> clazz;

    public AbstractLogicRESTIntegrationTest(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // find - one

    @Test
    public void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived() {
        final Response response = getAPI().findByUriAsResponse(getURI() + WebConstants.PATH_SEP + randomNumeric(6));

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(createNewEntity());

        // When
        final Response res = getAPI().findByUriAsResponse(uriForResourceCreation);

        // Then
        assertThat(res.getStatusCode(), is(200));
    }

    @Test
    @Ignore("this was written for a neo4j persistence engine, which treats null ids differently than Hibernate")
    public void whenResourceIsRetrievedByNegativeId_then409IsReceived() {
        final Long id = IDUtils.randomNegativeLong();

        // When
        final Response res = getAPI().findByUriAsResponse(getURI() + WebConstants.PATH_SEP + id);

        // Then
        assertThat(res.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // Given id is non numeric
        final String id = randomAlphabetic(6);

        // When
        final Response res = getAPI().findByUriAsResponse(getURI() + WebConstants.PATH_SEP + id);

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    @Test(expected = IllegalStateException.class)
    public void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenExceptionIsThrown() {
        getAPI().findOneByURI(getURI() + WebConstants.PATH_SEP + randomNumeric(8));
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getAPI().createAsURI(newResource);

        // When
        final T existingResource = getAPI().findOneByURI(uriOfExistingResource);

        // Then
        assertEquals(existingResource, newResource);
    }

    @Test(expected = IllegalStateException.class)
    /**/public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        getAPI().findOne(IDUtils.randomPositiveLong());
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrieved_thenResourceHasId() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getAPI().createAsURI(newResource);

        // When
        final T createdResource = getAPI().findOneByURI(uriOfExistingResource);

        // Then
        assertThat(createdResource.getId(), notNullValue());
    }

    // find one - by attributes

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().findOneByAttributes(SearchField.name.name(), existingResource.getName());
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenResourceIsFound() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByAttributes(SearchField.name.name(), existingResource.getName());

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final T resourceByName = getAPI().findOneByAttributes(SearchField.name.name(), existingResource.getName());

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNagatedNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().findAllByAttributes(QueryConstants.NAME_NEG, existingResource.getName());

        // Then
    }

    // find - all

    @Test
    public void whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getAPI().findAllAsResponse();

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenNoExceptions() {
        getAPI().findAll();
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenTheResultIsNotNull() {
        final List<T> resources = getAPI().findAll();

        assertNotNull(resources);
    }

    @Test
    /**/public void givenAnResourceExists_whenAllResourcesAreRetrieved_thenTheExistingResourceIsIndeedAmongThem() {
        final T existingResource = getAPI().create(createNewEntity());

        final List<T> resources = getAPI().findAll();

        assertThat(resources, hasItem(existingResource));
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() {
        // Given
        getAPI().createAsURI(createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    @Test
    /**/public void whenAllResourcesAreRetrieved_thenResourcesHaveIds() {
        // Given
        this.getAPI().createAsURI(createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // create

    @Test
    public void whenResourceIsCreated_then201IsReceived() {
        // When
        final Response response = getAPI().createAsResponse(createNewEntity());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public void whenNullResourceIsCreated_then415IsReceived() {
        // When
        final Response response = givenAuthenticated().contentType(getAPI().getMarshaller().getMime()).post(getURI());

        // Then
        assertThat(response.getStatusCode(), is(415));
    }

    @Test
    public void whenResourceIsCreatedWithNonNullId_then409IsReceived() {
        final T resourceWithId = createNewEntity();
        resourceWithId.setId(5l);

        // When
        final Response response = getAPI().createAsResponse(resourceWithId);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsCreated_thenALocationIsReturnedToTheClient() {
        // When
        final Response response = getAPI().createAsResponse(createNewEntity());

        // Then
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    @Ignore("this will not always pass at this time")
    public void givenResourceExists_whenResourceWithSameAttributesIsCreated_then409IsReceived() {
        // Given
        final T newEntity = createNewEntity();
        getAPI().createAsURI(newEntity);

        // When
        final Response response = getAPI().createAsResponse(newEntity);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsCreated_thenException() {
        getAPI().create(null);
    }

    @Test
    /**/public void whenResourceIsCreated_thenNoExceptions() {
        getAPI().createAsURI(createNewEntity());
    }

    @Test
    /**/public void whenResourceIsCreated_thenResourceIsRetrievable() {
        final T existingResource = getAPI().create(createNewEntity());

        assertNotNull(getAPI().findOne(existingResource.getId()));
    }

    @Test
    /**/public void whenResourceIsCreated_thenSavedResourceIsEqualToOriginalResource() {
        final T originalResource = createNewEntity();
        final T savedResource = getAPI().create(originalResource);

        assertEquals(originalResource, savedResource);
    }

    // update

    @Test(expected = RuntimeException.class)
    /**/public void whenNullResourceIsUpdated_thenException() {
        getAPI().update(null);
    }

    @Test
    public void givenInvalidResource_whenResourceIsUpdated_then409ConflictIsReceived() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());
        getEntityOps().invalidate(existingResource);

        // When
        final Response response = getAPI().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsUpdatedWithNullId_then409IsReceived() {
        // When
        final Response response = getAPI().updateAsResponse(createNewEntity());

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_then200IsReceived() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        final Response response = getAPI().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenNullResourceIsUpdated_then415IsReceived() {
        // Given
        // When
        final Response response = givenAuthenticated().contentType(getAPI().getMarshaller().getMime()).put(getURI());

        // Then
        assertThat(response.getStatusCode(), is(415));
    }

    @Test
    public void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived() {
        // Given
        final T unpersistedEntity = createNewEntity();
        unpersistedEntity.setId(IDUtils.randomPositiveLong());

        // When
        final Response response = getAPI().updateAsResponse(unpersistedEntity);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    /**/public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().update(existingResource);
    }

    @Test
    /**/public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getEntityOps().change(existingResource);
        getAPI().update(existingResource);

        final T updatedResourceFromServer = getAPI().findOne(existingResource.getId());

        // Then
        assertEquals(existingResource, updatedResourceFromServer);
    }

    // delete

    @Test
    public void whenResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
        // When
        final Response response = getAPI().deleteAsResponse(getURI() + randomAlphabetic(6));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived() {
        // When
        final Response response = getAPI().deleteAsResponse(getURI() + randomNumeric(6));

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourceExists_whenResourceIsDeleted_then204IsReceived() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(createNewEntity());

        // When
        final Response response = getAPI().deleteAsResponse(uriForResourceCreation);

        // Then
        assertThat(response.getStatusCode(), is(204));
    }

    @Test
    public void givenResourceExists_whenResourceIsDeleted_thenRetrievingResourceShouldResultIn404() {
        // Given
        final String uriOfResource = getAPI().createAsURI(createNewEntity());
        getAPI().deleteAsResponse(uriOfResource);

        // When
        final Response getResponse = getAPI().findByUriAsResponse(uriOfResource);

        // Then
        assertThat(getResponse.getStatusCode(), is(404));
    }

    @Test(expected = IllegalStateException.class)
    /**/public void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = getAPI().create(createNewEntity());

        // When
        getAPI().delete(existingResource.getId());

        // Then
        assertNull(getAPI().findOne(existingResource.getId()));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    protected final String getURI() {
        return getAPI().getURI() + WebConstants.PATH_SEP;
    }

    protected final RequestSpecification givenAuthenticated() {
        return getAPI().givenAuthenticated(null, null);
    }

}
