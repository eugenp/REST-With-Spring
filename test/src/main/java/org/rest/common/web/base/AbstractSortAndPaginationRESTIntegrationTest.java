package org.rest.common.web.base;

import static com.jayway.restassured.RestAssured.get;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.client.security.IClientAuthenticationComponent;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.util.SearchField;
import org.rest.common.util.order.OrderById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.google.common.collect.Ordering;
import com.jayway.restassured.response.Response;

public abstract class AbstractSortAndPaginationRESTIntegrationTest<T extends IEntity> {

    @Autowired
    protected IClientAuthenticationComponent auth;

    protected final Class<T> clazz;

    public AbstractSortAndPaginationRESTIntegrationTest(final Class<T> clazzToSet) {
        super();

        this.clazz = clazzToSet;
    }

    // tests

    // TODO: sort

    @Test
    public final void whenResourcesAreRetrievedPaginated_thenNoExceptions() {
        getAPI().findAllPaginatedAsResponse(1, 1);
    }

    @Test
    public final void whenResourcesAreRetrievedPaginated_then200IsReceived() {
        // When
        final Response response = getAPI().findAllPaginatedAsResponse(0, 1);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        getAPI().createAsResponse(getAPI().createNewEntity());

        // When
        final Response response = getAPI().findAllPaginatedAsResponse(0, 1);

        // Then
        assertFalse(getAPI().getMarshaller().decode(response.asString(), List.class).isEmpty());
    }

    @Test
    // - note: may fail intermittently - TODO: investigate
    public final void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        // When
        final Response response = getAPI().findAllPaginatedAsResponse(Integer.parseInt(randomNumeric(5)), 1);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPage_then400IsReceived() {
        // When
        final Response response = get(getURI() + "?page=" + randomAlphabetic(5).toLowerCase() + "&size=1");

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPageSize_then400IsReceived() {
        // When
        final Response response = get(getURI() + "?page=0" + "&size=" + randomAlphabetic(5));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // find - all (paginated)

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_thenNoExceptions() {
        getAPI().findAllPaginatedAndSortedAsResponse(0, 41, "name", null);
    }

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_then200IsReceived() {
        final Response response = getAPI().findAllPaginatedAndSortedAsResponse(0, 1, "name", Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenResourcesAreRetrievedSorted_then200IsReceived() {
        final Response response = getAPI().findAllSortedAsResponse("name", Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsResponse(getAPI().createNewEntity());
        getAPI().createAsResponse(getAPI().createNewEntity());

        // When
        final Response response = getAPI().findAllSortedAsResponse("name", Sort.Direction.ASC.name());
        final List<T> resourcesPaginatedAndSorted = getAPI().getMarshaller().decodeList(response.asString(), clazz);

        // Then
        assertTrue(getOrdering().isOrdered(resourcesPaginatedAndSorted));
    }

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsResponse(getAPI().createNewEntity());
        getAPI().createAsResponse(getAPI().createNewEntity());

        // When
        final Response response = getAPI().findAllPaginatedAndSortedAsResponse(0, 4, "name", Sort.Direction.ASC.name());
        final List<T> resourcesPaginatedAndSorted = getAPI().getMarshaller().decodeList(response.asString(), clazz);

        // Then
        assertTrue(getOrdering().isOrdered(resourcesPaginatedAndSorted));
    }

    @Test
    @Ignore("not necessarily true")
    public final void whenResourcesAreRetrievedPaginatedAndNotSorted_thenResourcesAreNotOrdered() {
        getAPI().createAsResponse(getAPI().createNewEntity());
        getAPI().createAsResponse(getAPI().createNewEntity());

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginated(0, 6);

        // Then
        assertFalse(getOrdering().isOrdered(resourcesPaginatedAndSorted));
    }

    @Test
    public final void whenResourcesAreRetrievedByPaginatedAndWithInvalidSorting_then400IsReceived() {
        // When
        final Response response = getAPI().findAllPaginatedAndSortedAsResponse(0, 4, "invalid", null);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // find - check order

    @Test
    public final void whenResourcesAreRetrievedSortedDescById_thenNoExceptions() {
        getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());
    }

    @Test
    public final void whenResourcesAreRetrievedSortedAscById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.ASC.name());

        assertTrue(new OrderById<T>().isOrdered(resourcesOrderedById));
    }

    @Test
    public final void whenResourcesAreRetrievedSortedDescById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());

        assertTrue(new OrderById<T>().reverse().isOrdered(resourcesOrderedById));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract Ordering<T> getOrdering();

    protected abstract String getURI();

    protected abstract T createNewEntity();

}
