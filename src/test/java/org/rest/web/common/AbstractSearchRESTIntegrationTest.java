package org.rest.web.common;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.rest.client.template.ClientOperations;
import org.rest.client.template.IEntityOperations;
import org.rest.client.template.IRESTTemplate;
import org.rest.common.INameableEntity;
import org.rest.util.IDUtils;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractSearchRESTIntegrationTest<T extends INameableEntity> {

    public AbstractSearchRESTIntegrationTest() {
	super();
    }

    // tests

    // search - by id

    @Test
    public final void givenResourceExists_whenResourceIfSearchedById_thenNoExceptions() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());
	getTemplate().searchAsResponse(existingResource.getId(), null);
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedById_then200IsReceived() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final Response searchResponse = getTemplate().searchAsResponse(existingResource.getId(), null);

	// Then
	assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByIdAndUnmarshalled_thenNoException() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());
	getTemplate().search(existingResource.getId(), null);
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByIdAndUnmarshalled_thenResourceIsFound() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final List<T> found = getTemplate().search(existingResource.getId(), null);

	// Then
	assertThat(found, hasItem(existingResource));
    }

    // search - by name

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByName_thenNoExceptions() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());
	getTemplate().searchAsResponse(null, existingResource.getName());
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByName_then200IsReceived() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final Response searchResponse = getTemplate().searchAsResponse(null, existingResource.getName());

	// Then
	assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByNameAndUnmarshalled_thenNoException() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());
	getTemplate().search(null, existingResource.getName());
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByNameAndUnmarshalled_thenResourceIsFound() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final List<T> found = getTemplate().search(null, existingResource.getName());

	// Then
	assertThat(found, hasItem(existingResource));
    }

    // search - by id and name

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByIdAndName_then200IsReceived() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final Response searchResponse = getTemplate().searchAsResponse(existingResource.getId(), existingResource.getName());

	// Then
	assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByIdAndNameAndUnmarshalled_thenResourceIsFound() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final List<T> found = getTemplate().search(existingResource.getId(), existingResource.getName());

	// Then
	assertThat(found, hasItem(existingResource));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByIdAndWrongNameAndUnmarshalled_thenResourceIsFound() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final List<T> found = getTemplate().search(existingResource.getId(), randomAlphabetic(8));

	// Then
	assertThat(found, not(hasItem(existingResource)));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByWrongIdAndCorrectNameAndUnmarshalled_thenResourceIsFound() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final List<T> found = getTemplate().search(IDUtils.randomPositiveLong(), existingResource.getName());

	// Then
	assertThat(found, not(hasItem(existingResource)));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByWrongIdAndWrongNameAndUnmarshalled_thenResourceIsFound() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	// When
	final List<T> found = getTemplate().search(IDUtils.randomPositiveLong(), randomAlphabetic(8));

	// Then
	assertThat(found, not(hasItem(existingResource)));
    }

    // search - by negated id,name

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByNegatedName_then200IsReceived() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	final ImmutablePair<String, ClientOperations> negatedNameConstraint = new ImmutablePair<String, ClientOperations>(existingResource.getName(), ClientOperations.NEG_EQ);

	// When
	final Response searchResponse = getTemplate().searchAsResponse(null, negatedNameConstraint);

	// Then
	assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourceExists_whenResourceIfSearchedByNegatedId_then200IsReceived() {
	final T existingResource = getTemplate().create(getTemplate().createNewEntity());

	final ImmutablePair<Long, ClientOperations> negatedIdConstraint = new ImmutablePair<Long, ClientOperations>(existingResource.getId(), ClientOperations.NEG_EQ);

	// When
	final Response searchResponse = getTemplate().searchAsResponse(negatedIdConstraint, null);

	// Then
	assertThat(searchResponse.getStatusCode(), is(200));
    }

    // template

    protected abstract IRESTTemplate<T> getTemplate();

    protected abstract IEntityOperations<T> getEntityOperations();

    protected final RequestSpecification givenAuthenticated() {
	return getTemplate().givenAuthenticated();
    }

}
