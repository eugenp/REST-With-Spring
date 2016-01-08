package org.baeldung.test.common.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.baeldung.common.spring.util.Profiles.CLIENT;
import static org.baeldung.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.baeldung.common.interfaces.INameableDto;
import org.baeldung.common.util.SearchField;
import org.baeldung.common.web.WebConstants;
import org.baeldung.test.common.client.template.IRestClient;
import org.baeldung.test.common.util.IDUtil;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
public abstract class AbstractReadOnlyLogicLiveTest<T extends INameableDto> {

    protected final Class<T> clazz;

    public AbstractReadOnlyLogicLiveTest(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // find - one

    @Test
    /*code*/public void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived() {
        final Response response = getApi().findOneAsResponse(IDUtil.randomPositiveLong());

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    /*code*/public void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // Given id is non numeric
        final String id = randomAlphabetic(6);

        // When
        final Response res = getApi().read(getUri() + WebConstants.PATH_SEP + id);

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    // find - all

    @Test
    /*code*/public final void whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().findAllAsResponse(null);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    // find - all - pagination

    @Test
    /*code*/public final void whenResourcesAreRetrievedPaginated_then200IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    // - note: may fail intermittently - TODO: investigate
    /*code*/public final void whenPageOfResourcesIsRetrievedOutOfBounds_then404IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(Integer.parseInt(randomNumeric(5)), 1);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    /*code*/public final void whenResourcesAreRetrievedWithNonNumericPage_then400IsReceived() {
        // When
        final Response response = givenReadAuthenticated().get(getUri() + "?page=" + randomAlphabetic(5).toLowerCase() + "&size=1");

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    /*code*/public final void whenResourcesAreRetrievedWithNonNumericPageSize_then400IsReceived() {
        // When
        final Response response = givenReadAuthenticated().get(getUri() + "?page=0" + "&size=" + randomAlphabetic(5));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // find - all - sorting

    @Test
    /*code*/public final void whenResourcesAreRetrievedSorted_then200IsReceived() {
        final Response response = getApi().findAllSortedAsResponse(SearchField.name.name(), Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    // find - all - pagination and sorting

    @Test
    /*code*/public final void whenResourcesAreRetrievedPaginatedAndSorted_then200IsReceived() {
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 1, SearchField.name.name(), Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    /*code*/public final void whenResourcesAreRetrievedByPaginatedAndWithInvalidSorting_then400IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 4, "invalid", null);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // count

    @Test
    /*code*/public final void whenCountIsPerformed_then200IsReceived() {
        // When
        final Response response = getApi().countAsResponse();

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    // template method

    protected abstract IRestClient<T> getApi();

    protected final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    protected final RequestSpecification givenReadAuthenticated() {
        return getApi().givenReadAuthenticated();
    }

}
