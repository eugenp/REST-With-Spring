package org.rest.common.web.base;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.util.IDUtils;
import org.rest.common.web.WebConstants;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractLogicRESTIntegrationTest<T extends IEntity> {

    protected final Class<T> clazz;

    public AbstractLogicRESTIntegrationTest(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // find - one

    @Test
    public void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions() {
        getAPI().findOneAsResponse(getURI() + WebConstants.PATH_SEP + randomNumeric(4));
    }

    @Test
    public void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived() {
        final Response response = getAPI().findOneAsResponse(getURI() + WebConstants.PATH_SEP + randomNumeric(6));

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final Response res = getAPI().findOneAsResponse(uriForResourceCreation);

        // Then
        assertThat(res.getStatusCode(), is(200));
    }

    @Test
    public void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        final T resource = getAPI().createNewEntity();
        final T existingResource = getAPI().create(resource);
        final T retrievedResource = getAPI().findOne(existingResource.getId());

        assertEquals(resource, retrievedResource);
    }

    @Test
    @Ignore("this was written for a neo4j persistence engine, which treats null ids differently than Hibernate")
    public void whenResourceIsRetrievedByNegativeId_then409IsReceived() {
        final Long id = IDUtils.randomNegativeLong();

        // When
        final Response res = getAPI().findOneAsResponse(getURI() + WebConstants.PATH_SEP + id);

        // Then
        assertThat(res.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // Given id is non numeric
        final String id = randomAlphabetic(6);

        // When
        final Response res = getAPI().findOneAsResponse(getURI() + WebConstants.PATH_SEP + id);

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    // find - all

    @Test
    public void whenResourcesAreRetrieved_thenNoExceptions() {
        getAPI().findOneAsResponse(getURI());
    }

    @Test
    public void whenResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getAPI().findAllAsResponse();

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() {
        // Given
        getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final List<T> allResources = getAPI().findAll();

        // Then
        assertThat(allResources, not(Matchers.<T> empty()));
    }

    // create

    @Test
    public void whenAResourceIsCreated_thenNoExceptions() {
        getAPI().createAsResponse(getAPI().createNewEntity());
    }

    @Test
    public void whenAResourceIsCreated_then201IsReceived() {
        // When
        final Response response = getAPI().createAsResponse(getAPI().createNewEntity());

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
    public void whenAResourceIsCreatedWithNonNullId_then409IsReceived() {
        final T resourceWithId = getAPI().createNewEntity();
        resourceWithId.setId(5l);

        // When
        final Response response = getAPI().createAsResponse(resourceWithId);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void whenAResourceIsCreated_thenALocationIsReturnedToTheClient() {
        // When
        final Response response = getAPI().createAsResponse(getAPI().createNewEntity());

        // Then
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    @Ignore("this will not always pass at this time")
    public void givenResourceExists_whenResourceWithSameAttributesIsCreated_then409IsReceived() {
        // Given
        final T newEntity = getAPI().createNewEntity();
        getAPI().createAsURI(newEntity);

        // When
        final Response response = getAPI().createAsResponse(newEntity);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    // update

    @Test
    // @Ignore("some resource may not have an invalid state")
    public void givenInvalidResource_whenResourceIsUpdated_then409ConflictIsReceived() {
        // Given
        final T existingResource = getAPI().create(getAPI().createNewEntity());
        getAPI().invalidate(existingResource);

        // When
        final Response response = getAPI().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void whenAResourceIsUpdatedWithNullId_then409IsReceived() {
        // When
        final Response response = getAPI().updateAsResponse(getAPI().createNewEntity());

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = getAPI().create(getAPI().createNewEntity());

        // When
        getAPI().updateAsResponse(existingResource);
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_then200IsReceived() {
        // Given
        final T existingResource = getAPI().create(getAPI().createNewEntity());

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
        final T unpersistedEntity = getAPI().createNewEntity();
        unpersistedEntity.setId(IDUtils.randomPositiveLong());

        // When
        final Response response = getAPI().updateAsResponse(unpersistedEntity);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
        // Given
        final T existingResource = getAPI().create(getAPI().createNewEntity());

        // When
        getAPI().change(existingResource);
        getAPI().update(existingResource);

        final T updatedResourceFromServer = getAPI().findOne(existingResource.getId());

        // Then
        assertEquals(existingResource, updatedResourceFromServer);
    }

    // delete

    @Test
    public void whenAResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
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
    public void givenResourceExist_whenResourceIsDeleted_then204IsReceived() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final Response response = getAPI().deleteAsResponse(uriForResourceCreation);

        // Then
        assertThat(response.getStatusCode(), is(204));
    }

    @Test
    public void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable() {
        // Given
        final String uriOfResource = getAPI().createAsURI(getAPI().createNewEntity());
        getAPI().deleteAsResponse(uriOfResource);

        // When
        final Response getResponse = getAPI().findOneAsResponse(uriOfResource);

        // Then
        assertThat(getResponse.getStatusCode(), is(404));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract String getURI();

    protected abstract RequestSpecification givenAuthenticated();

}
