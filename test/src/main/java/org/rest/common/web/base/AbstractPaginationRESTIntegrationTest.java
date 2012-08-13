package org.rest.common.web.base;

import static com.jayway.restassured.RestAssured.get;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.rest.common.client.security.IClientAuthenticationComponent;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.IEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractPaginationRESTIntegrationTest<T extends IEntity> {

    @Autowired
    protected IClientAuthenticationComponent auth;

    // tests

    // GET (paginated)

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

    // util

    protected abstract RequestSpecification givenAuthenticated();

    // template method

    protected abstract String getURI();

    protected abstract T createNewEntity();

    protected abstract IRESTTemplate<T> getAPI();

}
