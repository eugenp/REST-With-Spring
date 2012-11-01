package org.rest.common.web.base;

import static com.jayway.restassured.RestAssured.get;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.security.ITestAuthenticator;
import org.rest.common.client.template.IRestTemplate;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.util.SearchField;
import org.rest.common.util.order.OrderById;
import org.rest.common.util.order.OrderByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.restassured.response.Response;

@ActiveProfiles({ "client", "test", "mime_json" })
public abstract class AbstractSortAndPaginationRestLiveTest<T extends INameableEntity> {

    @Autowired
    protected ITestAuthenticator auth;

    protected final Class<T> clazz;

    public AbstractSortAndPaginationRestLiveTest(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

    // tests

    // find - all - pagination

    @Test
    /**/public final void whenResourcesAreRetrievedPaginated_thenNoExceptions() {
        getApi().findAllPaginated(1, 1);
    }

    @Test
    public final void whenResourcesAreRetrievedPaginated_then200IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1, null);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    // - note: may fail intermittently - TODO: investigate
    public final void whenPageOfResourcesIsRetrievedOutOfBounds_then404IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(Integer.parseInt(randomNumeric(5)), 1, null);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPage_then400IsReceived() {
        // When
        final Response response = get(getUri() + "?page=" + randomAlphabetic(5).toLowerCase() + "&size=1");

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPageSize_then400IsReceived() {
        // When
        final Response response = get(getUri() + "?page=0" + "&size=" + randomAlphabetic(5));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    /**/public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned() {
        getApi().createAsUri(createNewEntity(), null);

        // When
        final List<T> allPaginated = getApi().findAllPaginated(0, 1);

        // Then
        assertFalse(allPaginated.isEmpty());
    }

    // find - all - sorting

    @Test
    public final void whenResourcesAreRetrievedSorted_then200IsReceived() {
        final Response response = getApi().findAllSortedAsResponse(SearchField.name.name(), Sort.Direction.ASC.name(), null);

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        getApi().createAsUri(createNewEntity(), null);
        getApi().createAsUri(createNewEntity(), null);

        // When
        final List<T> resourcesSorted = getApi().findAllSorted(SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesSorted));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedDescById_thenNoExceptions() {
        getApi().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedAscById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getApi().findAllSorted(SearchField.id.toString(), Sort.Direction.ASC.name());

        assertTrue(new OrderById<T>().isOrdered(resourcesOrderedById));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedSortedDescById_thenResultsAreOrderedCorrectly() {
        final List<T> resourcesOrderedById = getApi().findAllSorted(SearchField.id.toString(), Sort.Direction.DESC.name());

        assertTrue(new OrderById<T>().reverse().isOrdered(resourcesOrderedById));
    }

    // find - all - pagination and sorting

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenNoExceptions() {
        getApi().findAllPaginatedAndSorted(0, 41, SearchField.name.name(), Sort.Direction.ASC.name());
    }

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_then200IsReceived() {
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 1, SearchField.name.name(), Sort.Direction.ASC.name(), null);

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenResourcesAreRetrievedByPaginatedAndWithInvalidSorting_then400IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 4, "invalid", null, null);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    /**/public final void whenResourcesAreRetrievedPaginatedAndSorted_thenResourcesAreIndeedOrdered() {
        getApi().createAsUri(createNewEntity(), null);
        getApi().createAsUri(createNewEntity(), null);

        // When
        final List<T> resourcesPaginatedAndSorted = getApi().findAllPaginatedAndSorted(0, 4, SearchField.name.name(), Sort.Direction.ASC.name());

        // Then
        assertTrue(new OrderByName<T>().isOrdered(resourcesPaginatedAndSorted));
    }

    // template method

    protected abstract IRestTemplate<T> getApi();

    protected abstract IEntityOperations<T> getEntityOps();

    protected abstract String getUri();

    protected abstract T createNewEntity();

}
