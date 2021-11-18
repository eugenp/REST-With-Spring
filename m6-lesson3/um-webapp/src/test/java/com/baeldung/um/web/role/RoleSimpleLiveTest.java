package com.baeldung.um.web.role;

import static com.baeldung.common.spring.util.Profiles.CLIENT;
import static com.baeldung.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.HttpHeaders;
import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.common.util.SearchField;
import com.baeldung.common.web.WebConstants;
import com.baeldung.test.common.util.IDUtil;
import com.baeldung.um.client.template.RoleSimpleApiClient;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.spring.CommonTestConfig;
import com.baeldung.um.spring.UmClientConfig;
import com.baeldung.um.spring.UmLiveTestConfig;
import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleSimpleLiveTest {

    private final static String JSON = MediaType.APPLICATION_JSON.toString();

    @Autowired
    private RoleSimpleApiClient api;

    // find - one

    @Test
    public final void whenNonExistingResourceIsRetrieved_then404IsReceived() {
        final Response response = getApi().findOneAsResponse(IDUtil.randomPositiveLong());

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // When
        final Response res = getApi().read(getUri() + WebConstants.PATH_SEP + randomAlphabetic(6));

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    @Test
    public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved() {
        // Given
        final long id = getApi().create(createNewResource()).getId();

        // When
        final Response res = getApi().findOneAsResponse(id);

        // Then
        assertThat(res.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved() {
        // Given, When
        final Role newResource = createNewResource();
        final Role createdResource = getApi().create(newResource);

        // Then
        assertEquals(createdResource, newResource);
    }

    // find - all

    @Test
    public final void whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().read(getUri());

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    // find - all - pagination

    @Test
    public final void whenResourcesAreRetrievedPaginated_then200IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenPageOfResourcesIsRetrievedOutOfBounds_then404IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(Integer.parseInt(randomNumeric(5)), 1);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPage_then400IsReceived() {
        // When
        final Response response = givenAuthenticated().get(getUri() + "?page=" + randomAlphabetic(5).toLowerCase() + "&size=1");

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPageSize_then400IsReceived() {
        // When
        final Response response = givenAuthenticated().get(getUri() + "?page=0" + "&size=" + randomAlphabetic(5));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // find - all - sorting

    @Test
    public final void whenResourcesAreRetrievedSorted_then200IsReceived() {
        final Response response = getApi().findAllSortedAsResponse(SearchField.name.name(), Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    // find - all - pagination and sorting

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_then200IsReceived() {
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 1, SearchField.name.name(), Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenResourcesAreRetrievedByPaginatedAndWithInvalidSorting_then400IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 4, "invalid", null);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // create

    @Test
    public final void whenResourceIsCreated_then201IsReceived() {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public final void givenResourceHasNameWithSpace_whenResourceIsCreated_then201IsReceived() {
        final Role newResource = createNewResource();
        newResource.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));

        // When
        final Response createAsResponse = getApi().createAsResponse(newResource);

        // Then
        assertThat(createAsResponse.getStatusCode(), is(201));
    }

    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final Role newResource = createNewResource();
        newResource.getPrivileges().add(createNewAssociationResource());

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived() {
        final Privilege invalidAssociation = createNewAssociationResource();
        invalidAssociation.setName(null);
        final Role newResource = createNewResource();
        newResource.getPrivileges().add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceWithUnsupportedMediaTypeIsCreated_then415IsReceived() {
        // When
        final Response response = givenAuthenticated().contentType("unknown").post(getUri());

        // Then
        assertThat(response.getStatusCode(), is(415));
    }

    @Test
    public final void whenResourceIsCreatedWithNonNullId_then409IsReceived() {
        final Role resourceWithId = createNewResource();
        resourceWithId.setId(5l);

        // When
        final Response response = getApi().createAsResponse(resourceWithId);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceIsCreated_thenResponseContainsTheLocationHeader() {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    // not a conflict - it is a bad request due to constraint violation
    @Test
    public final void givenResourceExists_whenResourceWithSameAttributesIsCreated_then400IsReceived() {
        // Given
        final Role newResource = createNewResource();
        getApi().createAsResponse(newResource);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // update

    @Test
    public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived() {
        // Given
        final Role existingResource = getApi().create(createNewResource());

        // When
        final Response response = getApi().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void givenInvalidResource_whenResourceIsUpdated_then400BadRequestIsReceived() {
        // Given
        final Role existingResource = getApi().create(createNewResource());
        existingResource.setName(null);

        // When
        final Response response = getApi().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenResourceIsUpdatedWithNullId_then400IsReceived() {
        // When
        final Response response = getApi().updateAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenNullResourceIsUpdated_then400IsReceived() {
        // When
        final Response response = givenAuthenticated().contentType(JSON).put(getUri() + "/" + randomAlphanumeric(4));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived() {
        // Given
        final Role unpersistedResource = createNewResource();
        unpersistedResource.setId(IDUtil.randomPositiveLong());

        // When
        final Response response = getApi().updateAsResponse(unpersistedResource);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    // delete

    @Test
    public final void givenResourceExists_whenResourceIsDeleted_then204IsReceived() {
        // Given
        final long idOfResource = getApi().create(createNewResource()).getId();

        // When
        final Response response = getApi().deleteAsResponse(idOfResource);

        // Then
        assertThat(response.getStatusCode(), is(204));
    }

    @Test
    public final void whenResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
        // When
        final Response response = getApi().givenAuthenticated().delete(getUri() + randomAlphabetic(6));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived() {
        // When
        final Response response = getApi().deleteAsResponse(Long.parseLong(randomNumeric(6)));

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void givenResourceExistedAndWasDeleted_whenRetrievingResource_then404IsReceived() {
        // Given
        final long idOfResource = getApi().create(createNewResource()).getId();
        getApi().deleteAsResponse(idOfResource);

        // When
        final Response getResponse = getApi().findOneAsResponse(idOfResource);

        // Then
        assertThat(getResponse.getStatusCode(), is(404));
    }

    // mime

    @Test
    public final void givenRequestAcceptsMime_whenResourceIsRetrievedById_thenResponseContentTypeIsMime() {
        // Given
        final long idOfCreatedResource = getApi().create(createNewResource()).getId();

        // When
        final Response res = getApi().findOneAsResponse(idOfCreatedResource);

        // Then
        assertThat(res.getContentType(), StringContains.containsString(MediaType.APPLICATION_JSON.toString()));
    }

    // UTIL

    private final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    private final RoleSimpleApiClient getApi() {
        return api;
    }

    private final RequestSpecification givenAuthenticated() {
        return getApi().givenAuthenticated();
    }

    private final Privilege createNewAssociationResource() {
        return new Privilege(randomAlphabetic(8));
    }

    private final Role createNewResource() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }

}
