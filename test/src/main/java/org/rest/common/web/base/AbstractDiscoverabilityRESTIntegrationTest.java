package org.rest.common.web.base;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.matchers.JUnitMatchers.containsString;

import org.hamcrest.core.AnyOf;
import org.junit.Test;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.util.LinkUtil;
import org.rest.common.web.util.HTTPLinkHeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractDiscoverabilityRESTIntegrationTest<T extends IEntity> {

    private Class<T> clazz;

    @Autowired
    @Qualifier("xStreamMarshaller")
    IMarshaller marshaller;

    public AbstractDiscoverabilityRESTIntegrationTest(final Class<T> clazzToSet) {
        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // GET (single)

    @Test
    public final void whenResourceIsRetrieved_thenURIToGetAllResourcesIsDiscoverable() {
        // Given
        final String uriOfExistingResource = getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final Response getResponse = getAPI().findOneAsResponse(uriOfExistingResource);

        // Then
        final String uriToAllResources = HTTPLinkHeaderUtils.extractURIByRel(getResponse.getHeader(HttpHeaders.LINK), LinkUtil.REL_COLLECTION);

        final Response getAllResponse = getAPI().findOneAsResponse(uriToAllResources);
        assertThat(getAllResponse.getStatusCode(), is(200));
    }

    // GET (paged)

    @Test
    public final void whenFirstPageOfResourcesIsRetrieved_thenSomethingIsDiscoverable() {
        // When
        final Response response = getAPI().findOneAsResponse(getURI() + "?page=1&size=10");

        // Then
        final String linkHeader = response.getHeader(HttpHeaders.LINK);
        assertNotNull(linkHeader);
    }

    @Test
    public final void whenFirstPageOfResourcesIsRetrieved_thenNextPageIsDiscoverable() {
        getAPI().createAsURI(getAPI().createNewEntity());
        getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final Response response = getAPI().findOneAsResponse(getURI() + "?page=1&size=1");

        // Then
        final String uriToNextPage = HTTPLinkHeaderUtils.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertNotNull(uriToNextPage);
    }

    @Test
    public final void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsDiscoverable() {
        // When
        final Response response = getAPI().findOneAsResponse(getURI() + "?page=1&size=1");

        // Then
        final String uriToNextPage = HTTPLinkHeaderUtils.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertEquals(getURI() + "?page=2&size=1", uriToNextPage);
    }

    @Test
    public final void whenPageOfResourcesIsRetrieved_thenLastPageIsDiscoverable() {
        getAPI().create(getAPI().createNewEntity());
        getAPI().create(getAPI().createNewEntity());

        // When
        final Response response = getAPI().findOneAsResponse(getURI() + "?page=0&size=1");

        // Then
        final String uriToLastPage = HTTPLinkHeaderUtils.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_LAST);
        assertNotNull(uriToLastPage);
    }

    @Test
    public final void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable() {
        // When
        final Response response = getAPI().findOneAsResponse(getURI() + "?page=1&size=1");
        final String uriToLastPage = HTTPLinkHeaderUtils.extractURIByRel(response.getHeader(HttpHeaders.LINK), LinkUtil.REL_LAST);

        // Then
        final Response responseForLastPage = getAPI().findOneAsResponse(uriToLastPage);
        final String uriToNextPage = HTTPLinkHeaderUtils.extractURIByRel(responseForLastPage.getHeader(HttpHeaders.LINK), LinkUtil.REL_NEXT);
        assertNull(uriToNextPage);
    }

    // POST

    @Test
    public final void whenInvalidPOSTIsSentToValidURIOfResource_thenAllowHeaderListsTheAllowedActions() {
        // Given
        final String uriOfExistingResource = getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final Response res = givenAuthenticated().post(uriOfExistingResource);

        // Then
        final String allowHeader = res.getHeader(HttpHeaders.ALLOW);
        assertThat(allowHeader, AnyOf.<String> anyOf(containsString("GET"), containsString("PUT"), containsString("DELETE")));
    }

    @Test
    public final void whenResourceIsCreated_thenURIOfTheNewlyCreatedResourceIsDiscoverable() {
        // When
        final T unpersistedResource = createNewEntity();
        final String uriOfNewlyCreatedResource = getAPI().createAsURI(unpersistedResource);

        // Then
        final Response response = getAPI().findOneAsResponse(uriOfNewlyCreatedResource);
        final T resourceFromServer = marshaller.decode(response.body().asString(), clazz);
        assertThat(unpersistedResource, equalTo(resourceFromServer));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract String getURI();

    protected abstract void change(final T resource);

    protected abstract T createNewEntity();

    protected abstract RequestSpecification givenAuthenticated();

}
