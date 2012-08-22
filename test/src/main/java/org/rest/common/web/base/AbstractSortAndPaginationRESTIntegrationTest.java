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
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.security.IClientAuthenticationComponent;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.util.SearchField;
import org.rest.common.util.order.OrderById;
import org.rest.common.util.order.OrderByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.jayway.restassured.response.Response;

public abstract class AbstractSortAndPaginationRESTIntegrationTest<T extends INameableEntity> {

    @Autowired
    protected IClientAuthenticationComponent auth;

    protected final Class<T> clazz;

    public AbstractSortAndPaginationRESTIntegrationTest(final Class<T> clazzToSet) {
        super();

        this.clazz = clazzToSet;
    }

    // tests

    // find - all - pagination

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
    /**/public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> allPaginated = getAPI().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    @Test
    // - note: may fail intermittently - TODO: investigate
    public final void whenPageOfResourcesIsRetrievedOutOfBounds_then404IsReceived() {
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

    @Test
    @Ignore("not necessarily true")
    public final void whenResourcesAreRetrievedPaginatedAndNotSorted_thenResourcesAreNotOrdered() {
        getAPI().createAsURI(getEntityOps().createNewEntity());
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginated(0, 6);

        // Then
        assertFalse(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    // find - all - sorting

    @Test
    public final void whenResourcesAreRetrievedSorted_then200IsReceived() {
        final Response response = getAPI().findAllSortedAsResponse("name", Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsURI(getEntityOps().createNewEntity());
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> resourcesSorted = getAPI().findAllSorted("name", Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesSorted));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedDescById_thenNoExceptions() {
        getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedAscById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.ASC.name());

        assertTrue(new OrderById<T>().isOrdered(resourcesOrderedById));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedDescById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getAPI().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());

        assertTrue(new OrderById<T>().reverse().isOrdered(resourcesOrderedById));
    }

    // find - all - pagination and sorting

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
    public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        getAPI().createAsURI(getEntityOps().createNewEntity());
        getAPI().createAsURI(getEntityOps().createNewEntity());

        // When
        final List<T> resourcesPaginatedAndSorted = getAPI().findAllPaginatedAndSorted(0, 4, "name", Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    @Test
    public final void whenResourcesAreRetrievedByPaginatedAndWithInvalidSorting_then400IsReceived() {
        // When
        final Response response = getAPI().findAllPaginatedAndSortedAsResponse(0, 4, "invalid", null);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected abstract String getURI();

    protected abstract T createNewEntity();

}
