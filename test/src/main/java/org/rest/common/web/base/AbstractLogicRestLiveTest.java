package org.rest.common.web.base;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
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
import static org.rest.common.search.ClientOperation.EQ;
import static org.rest.common.search.ClientOperation.NEG_EQ;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.template.IRestTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.IDUtil;
import org.rest.common.util.SearchField;
import org.rest.common.web.WebConstants;
import org.springframework.test.context.ActiveProfiles;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@ActiveProfiles({ "client", "test", "mime_json" })
public abstract class AbstractLogicRestLiveTest<T extends INameableEntity> {

    protected final Class<T> clazz;

    public AbstractLogicRestLiveTest(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // find - one

    @Test
    public void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived() {
        final Response response = getApi().findOneByUriAsResponse(getUri() + WebConstants.PATH_SEP + randomNumeric(6), null);

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved() {
        // Given
        final String uriForResourceCreation = getApi().createAsUri(createNewEntity(), null);

        // When
        final Response res = getApi().findOneByUriAsResponse(uriForResourceCreation, null);

        // Then
        assertThat(res.getStatusCode(), is(200));
    }

    @Test
    @Ignore("this was written for a neo4j persistence engine, which treats null ids differently than Hibernate")
    public void whenResourceIsRetrievedByNegativeId_then409IsReceived() {
        final Long id = IDUtil.randomNegativeLong();

        // When
        final Response res = getApi().findOneByUriAsResponse(getUri() + WebConstants.PATH_SEP + id, null);

        // Then
        assertThat(res.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // Given id is non numeric
        final String id = randomAlphabetic(6);

        // When
        final Response res = getApi().findOneByUriAsResponse(getUri() + WebConstants.PATH_SEP + id, null);

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    @Test(expected = IllegalStateException.class)
    public void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenException() {
        getApi().findOneByUri(getUri() + WebConstants.PATH_SEP + randomNumeric(8), null);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getApi().createAsUri(newResource, null);

        // When
        final T existingResource = getApi().findOneByUri(uriOfExistingResource, null);

        // Then
        assertEquals(existingResource, newResource);
    }

    @Test(expected = IllegalStateException.class)
    /**/public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
        // When
        getApi().findOne(IDUtil.randomPositiveLong());
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsRetrieved_thenResourceHasId() {
        // Given
        final T newResource = createNewEntity();
        final String uriOfExistingResource = getApi().createAsUri(newResource, null);

        // When
        final T createdResource = getApi().findOneByUri(uriOfExistingResource, null);

        // Then
        assertThat(createdResource.getId(), notNullValue());
    }

    // find one - by attributes

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        getApi().searchOne(nameConstraint);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenResourceIsFound() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        final T resourceByName = getApi().searchOne(nameConstraint);

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        final T resourceByName = getApi().searchOne(nameConstraint);

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNagatedNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), NEG_EQ, existingResource.getName());
        getApi().searchAll(nameConstraint);

        // Then
    }

    // find - all

    @Test
    public void whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().findAllAsResponse(null);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

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
        getApi().createAsUri(createNewEntity(), null);

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
        // Given
        getApi().createAsUri(createNewEntity(), null);

        // When
        final List<T> allResources = getApi().findAll();

        // Then
        for (final T resource : allResources) {
            assertNotNull(resource.getId());
        }
    }

    // create

    @Test
    public void whenResourceIsCreated_then201IsReceived() {
        // When
        final Response response = getApi().createAsResponse(createNewEntity());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public void whenNullResourceIsCreated_then415IsReceived() {
        // When
        final Response response = givenAuthenticated().contentType(getApi().getMarshaller().getMime()).post(getUri());

        // Then
        assertThat(response.getStatusCode(), is(415));
    }

    @Test
    public void whenResourceIsCreatedWithNonNullId_then409IsReceived() {
        final T resourceWithId = createNewEntity();
        resourceWithId.setId(5l);

        // When
        final Response response = getApi().createAsResponse(resourceWithId);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsCreated_thenALocationIsReturnedToTheClient() {
        // When
        final Response response = getApi().createAsResponse(createNewEntity());

        // Then
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    @Ignore("this will not always pass at this time")
    public void givenResourceExists_whenResourceWithSameAttributesIsCreated_then409IsReceived() {
        // Given
        final T newEntity = createNewEntity();
        getApi().createAsUri(newEntity, null);

        // When
        final Response response = getApi().createAsResponse(newEntity);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

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
    public void givenInvalidResource_whenResourceIsUpdated_then409ConflictIsReceived() {
        // Given
        final T existingResource = getApi().create(createNewEntity());
        getEntityOps().invalidate(existingResource);

        // When
        final Response response = getApi().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public void whenResourceIsUpdatedWithNullId_then400IsReceived() {
        // When
        final Response response = getApi().updateAsResponse(createNewEntity());

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public void givenResourceExists_whenResourceIsUpdated_then200IsReceived() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        final Response response = getApi().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenNullResourceIsUpdated_then400IsReceived() {
        // Given
        // When
        final Response response = givenAuthenticated().contentType(getApi().getMarshaller().getMime()).put(getUri() + "/" + randomAlphanumeric(4));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived() {
        // Given
        final T unpersistedEntity = createNewEntity();
        unpersistedEntity.setId(IDUtil.randomPositiveLong());

        // When
        final Response response = getApi().updateAsResponse(unpersistedEntity);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    /**/public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().update(existingResource);
    }

    @Test
    /**/public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
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
    public void whenResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
        // When
        final Response response = getApi().deleteAsResponse(getUri() + randomAlphabetic(6));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived() {
        // When
        final Response response = getApi().deleteAsResponse(getUri() + randomNumeric(6));

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourceExists_whenResourceIsDeleted_then204IsReceived() {
        // Given
        final String uriForResourceCreation = getApi().createAsUri(createNewEntity(), null);

        // When
        final Response response = getApi().deleteAsResponse(uriForResourceCreation);

        // Then
        assertThat(response.getStatusCode(), is(204));
    }

    @Test
    public void givenResourceExists_whenResourceIsDeleted_thenRetrievingResourceShouldResultIn404() {
        // Given
        final String uriOfResource = getApi().createAsUri(createNewEntity(), null);
        getApi().deleteAsResponse(uriOfResource);

        // When
        final Response getResponse = getApi().findOneByUriAsResponse(uriOfResource, null);

        // Then
        assertThat(getResponse.getStatusCode(), is(404));
    }

    @Test(expected = IllegalStateException.class)
    /**/public void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
        // Given
        final T existingResource = getApi().create(createNewEntity());

        // When
        getApi().delete(existingResource.getId());

        // Then
        assertNull(getApi().findOne(existingResource.getId()));
    }

    // template method

    protected abstract IRestTemplate<T> getApi();

    protected abstract IEntityOperations<T> getEntityOps();

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    protected final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    protected final RequestSpecification givenAuthenticated() {
        return getApi().givenAuthenticated();
    }

}
