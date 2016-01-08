package org.baeldung.test.common.web;

import static org.baeldung.common.spring.util.Profiles.CLIENT;
import static org.baeldung.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.baeldung.client.IDtoOperations;
import org.baeldung.client.marshall.IMarshaller;
import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.util.LinkUtil;
import org.baeldung.test.common.client.template.IRestClient;
import org.baeldung.test.common.web.util.HTTPLinkHeaderUtil;
import org.hamcrest.core.AnyOf;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.jayway.restassured.config.RedirectConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
public abstract class AbstractDiscoverabilityLiveTest<T extends IDto> {

    private Class<T> clazz;

    @Autowired
    private IMarshaller marshaller;

    public AbstractDiscoverabilityLiveTest(final Class<T> clazzToSet) {
        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // redirects

    @Test
    public void whenConsumingSimillarResourceName_thenRedirectedToCorrectResourceName() {
        final String simillarUriOfResource = getUri().substring(0, getUri().length() - 1);

        final RequestSpecification givenAuthenticated = getApi().givenReadAuthenticated();

        final RequestSpecification readReq = givenAuthenticated.header(HttpHeaders.ACCEPT, marshaller.getMime());
        final RequestSpecification customRequest = readReq.config(new RestAssuredConfig().redirect(new RedirectConfig().followRedirects(false)));
        final Response responseOfSimillarUri = getApi().findOneByUriAsResponse(simillarUriOfResource, customRequest);
        assertThat(responseOfSimillarUri.getStatusCode(), is(301));
    }

    // GET (single)

    @Test
    public final void whenResourceIsRetrieved_thenUriToGetAllResourcesIsDiscoverable() {
        // Given
        final String uriOfExistingResource = getApi().createAsUri(createNewResource());

        // When
        final Response getResponse = getApi().read(uriOfExistingResource);

        // Then
        final String uriToAllResources = HTTPLinkHeaderUtil.extractURIByRel(getResponse.getHeader(HttpHeaders.LINK), LinkUtil.REL_COLLECTION);

        final Response getAllResponse = getApi().read(uriToAllResources);
        assertThat(getAllResponse.getStatusCode(), is(200));
    }

    // GET (paginated)

    @Test
    public final void whenFirstPageOfResourcesIsRetrieved_thenSomethingIsDiscoverable() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(1, 10);

        // Then
        final String linkHeader = response.getHeader(HttpHeaders.LINK);
        assertNotNull(linkHeader);
    }

    @Test
    public final void whenFirstPageOfResourcesIsRetrieved_thenNextPageIsDiscoverable() {
        getApi().createAsUri(createNewResource());
        getApi().createAsUri(createNewResource());

        // When
        final Response response = getApi().findAllPaginatedAsResponse(1, 1);

        // Then
        final String uriToNextPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertNotNull(uriToNextPage);
    }

    @Test
    public final void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsDiscoverable() {
        getApi().createAsUri(createNewResource());
        getApi().createAsUri(createNewResource());

        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1);

        // Then
        final String uriToNextPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertEquals(getUri() + "?page=1&size=1", uriToNextPage);
    }

    @Test
    public final void whenPageOfResourcesIsRetrieved_thenLastPageIsDiscoverable() {
        getApi().create(createNewResource());
        getApi().create(createNewResource());

        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1);

        // Then
        final String uriToLastPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_LAST);
        assertNotNull(uriToLastPage);
    }

    @Test
    public final void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(1, 1);
        final String uriToLastPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_LAST);

        // Then
        final Response responseForLastPage = getApi().read(uriToLastPage);
        final String uriToNextPage = HTTPLinkHeaderUtil.extractURIByRel(responseForLastPage.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertNull(uriToNextPage);
    }

    // POST

    @Test
    public final void whenInvalidPOSTIsSentToValidUriOfResource_thenAllowHeaderListsTheAllowedActions() {
        // Given
        final String uriOfExistingResource = getApi().createAsUri(createNewResource());

        // When
        final Response res = getApi().givenReadAuthenticated().post(uriOfExistingResource);

        // Then
        final String allowHeader = res.getHeader(HttpHeaders.ALLOW);
        assertThat(allowHeader, AnyOf.<String> anyOf(containsString("GET"), containsString("PUT"), containsString("DELETE")));
    }

    @Test
    public final void whenResourceIsCreated_thenUriOfTheNewlyCreatedResourceIsDiscoverable() {
        // When
        final T unpersistedResource = createNewResource();
        final String uriOfNewlyCreatedResource = getApi().createAsUri(unpersistedResource);

        // Then
        final Response response = getApi().read(uriOfNewlyCreatedResource);
        final T resourceFromServer = marshaller.decode(response.body().asString(), clazz);
        assertThat(unpersistedResource, equalTo(resourceFromServer));
    }

    // template method

    protected abstract IRestClient<T> getApi();

    protected abstract IDtoOperations<T> getEntityOps();

    protected abstract String getUri();

    protected abstract T createNewResource();

}
