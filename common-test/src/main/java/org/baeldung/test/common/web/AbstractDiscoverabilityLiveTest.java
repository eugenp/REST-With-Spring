package org.baeldung.test.common.web;

import static org.baeldung.common.spring.util.Profiles.CLIENT;
import static org.baeldung.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.matchers.JUnitMatchers.containsString;

import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.client.IDtoOperations;
import org.baeldung.client.marshall.IMarshaller;
import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.util.LinkUtil;
import org.baeldung.test.common.client.security.ITestAuthenticator;
import org.baeldung.test.common.client.template.IRestTemplate;
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

    @Autowired
    protected ITestAuthenticator auth;

    public AbstractDiscoverabilityLiveTest(final Class<T> clazzToSet) {
        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // redirects

    @Test
    public void whenConsumingSimillarResourceName_thenRedirectedToCorrectResourceName() {
        final String simillarUriOfResource = getUri().substring(0, getUri().length() - 1);
        final Pair<String, String> readCredentials = getApi().getReadCredentials();
        final RequestSpecification givenAuthenticated = auth.givenBasicAuthenticated(readCredentials.getLeft(), readCredentials.getRight());
        final RequestSpecification readReq = givenAuthenticated.header(HttpHeaders.ACCEPT, marshaller.getMime());
        final RequestSpecification customRequest = readReq.config(new RestAssuredConfig().redirect(new RedirectConfig().followRedirects(false)));
        final Response responseOfSimillarUri = getApi().findOneByUriAsResponse(simillarUriOfResource, customRequest);
        assertThat(responseOfSimillarUri.getStatusCode(), is(301));
    }

    // GET (single)

    @Test
    public final void whenResourceIsRetrieved_thenURIToGetAllResourcesIsDiscoverable() {
        // Given
        final String uriOfExistingResource = getApi().createAsUri(createNewEntity());

        // When
        final Response getResponse = getApi().findOneByUriAsResponse(uriOfExistingResource, null);

        // Then
        final String uriToAllResources = HTTPLinkHeaderUtil.extractURIByRel(getResponse.getHeader(HttpHeaders.LINK), LinkUtil.REL_COLLECTION);

        final Response getAllResponse = getApi().findAllByUriAsResponse(uriToAllResources, null);
        assertThat(getAllResponse.getStatusCode(), is(200));
    }

    // GET (paginated)

    @Test
    public final void whenFirstPageOfResourcesIsRetrieved_thenSomethingIsDiscoverable() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(1, 10, null);

        // Then
        final String linkHeader = response.getHeader(HttpHeaders.LINK);
        assertNotNull(linkHeader);
    }

    @Test
    public final void whenFirstPageOfResourcesIsRetrieved_thenNextPageIsDiscoverable() {
        getApi().createAsUri(createNewEntity());
        getApi().createAsUri(createNewEntity());

        // When
        final Response response = getApi().findAllPaginatedAsResponse(1, 1, null);

        // Then
        final String uriToNextPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertNotNull(uriToNextPage);
    }

    @Test
    public final void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsDiscoverable() {
        getApi().createAsUri(createNewEntity());
        getApi().createAsUri(createNewEntity());

        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1, null);

        // Then
        final String uriToNextPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertEquals(getUri() + "?page=1&size=1", uriToNextPage);
    }

    @Test
    public final void whenPageOfResourcesIsRetrieved_thenLastPageIsDiscoverable() {
        getApi().create(createNewEntity());
        getApi().create(createNewEntity());

        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1, null);

        // Then
        final String uriToLastPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_LAST);
        assertNotNull(uriToLastPage);
    }

    @Test
    public final void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(1, 1, null);
        final String uriToLastPage = HTTPLinkHeaderUtil.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_LAST);

        // Then
        final Response responseForLastPage = getApi().findAllByUriAsResponse(uriToLastPage, null);
        final String uriToNextPage = HTTPLinkHeaderUtil.extractURIByRel(responseForLastPage.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertNull(uriToNextPage);
    }

    // POST

    @Test
    public final void whenInvalidPOSTIsSentToValidURIOfResource_thenAllowHeaderListsTheAllowedActions() {
        // Given
        final String uriOfExistingResource = getApi().createAsUri(createNewEntity());

        // When
        final Response res = getApi().givenReadAuthenticated().post(uriOfExistingResource);

        // Then
        final String allowHeader = res.getHeader(HttpHeaders.ALLOW);
        assertThat(allowHeader, AnyOf.<String> anyOf(containsString("GET"), containsString("PUT"), containsString("DELETE")));
    }

    @Test
    public final void whenResourceIsCreated_thenURIOfTheNewlyCreatedResourceIsDiscoverable() {
        // When
        final T unpersistedResource = createNewEntity();
        final String uriOfNewlyCreatedResource = getApi().createAsUri(unpersistedResource);

        // Then
        final Response response = getApi().findOneByUriAsResponse(uriOfNewlyCreatedResource, null);
        final T resourceFromServer = marshaller.decode(response.body().asString(), clazz);
        assertThat(unpersistedResource, equalTo(resourceFromServer));
    }

    // template method

    protected abstract IRestTemplate<T> getApi();

    protected abstract IDtoOperations<T> getEntityOps();

    protected abstract String getUri();

    protected abstract T createNewEntity();

}
