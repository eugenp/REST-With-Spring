package org.rest.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.rest.client.marshall.IMarshaller;
import org.rest.common.IEntity;
import org.rest.sec.util.SearchTestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;

/**
 * Template for the consumption of the REST API <br>
 */
public abstract class AbstractRESTTemplate<T extends IEntity> implements IRESTTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("xstreamMarshaller")
    protected IMarshaller marshaller;

    protected final Class<T> clazz;

    public AbstractRESTTemplate(final Class<T> clazzToSet) {
	super();

	Preconditions.checkNotNull(clazzToSet);
	clazz = clazzToSet;
    }

    // find - one

    @Override
    public T findOne(final long id) {
	final String resourceAsMime = findOneAsMime(getURI() + "/" + id);

	return marshaller.decode(resourceAsMime, clazz);
    }

    @Override
    public Response findOneAsResponse(final String uriOfResource) {
	return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime()).get(uriOfResource);
    }

    @Override
    public Response findOneAsResponse(final String uriOfResource, final String acceptMime) {
	return givenAuthenticated().header(HttpHeaders.ACCEPT, acceptMime).get(uriOfResource);
    }

    // find - all

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<T> findAll() {
	final Response findAllResponse = findOneAsResponse(getURI());
	return marshaller.<List> decode(findAllResponse.getBody().asString(), List.class);
    }

    @Override
    public Response findAllAsResponse() {
	return findOneAsResponse(getURI());
    }

    // create

    @Override
    public T create(final T resource) {
	final String uriForResourceCreation = createResourceAsURI(resource);
	final String resourceAsXML = findOneAsMime(uriForResourceCreation);

	return marshaller.decode(resourceAsXML, clazz);
    }

    @Override
    public String createResourceAsURI(final T resource) {
	Preconditions.checkNotNull(resource);

	final String resourceAsString = marshaller.encode(resource);
	final Response response = givenAuthenticated().contentType(marshaller.getMime()).body(resourceAsString).post(getURI());
	Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

	final String locationOfCreatedResource = response.getHeader(HttpHeaders.LOCATION);
	Preconditions.checkNotNull(locationOfCreatedResource);
	return locationOfCreatedResource;
    }

    @Override
    public Response createAsResponse(final T resource) {
	Preconditions.checkNotNull(resource);

	final String resourceAsString = marshaller.encode(resource);
	logger.debug("Creating Resource against URI: " + getURI());
	return givenAuthenticated().contentType(marshaller.getMime()).body(resourceAsString).post(getURI());
    }

    // update

    @Override
    public void update(final T resource) {
	final Response response = updateAsResponse(resource);
	Preconditions.checkState(response.getStatusCode() == 200, "update operation: " + response.getStatusCode());
    }

    @Override
    public Response updateAsResponse(final T resource) {
	Preconditions.checkNotNull(resource);

	final String resourceAsString = marshaller.encode(resource);
	return givenAuthenticated().contentType(marshaller.getMime()).body(resourceAsString).put(getURI());
    }

    // delete

    @Override
    public void deleteAll() {
	throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final long id) {
	final Response deleteResponse = deleteAsResponse(getURI() + "/" + id);
	Preconditions.checkState(deleteResponse.getStatusCode() == 204);
    }

    @Override
    public Response deleteAsResponse(final String uriOfResource) {
	return givenAuthenticated().delete(uriOfResource);
    }

    // search

    @Override
    public final Response searchAsResponse(final Pair<Long, ClientOperations> idOp, final Pair<String, ClientOperations> nameOp) {
	final String queryURI = getURI() + "?q=" + SearchTestUtil.constructQueryString(idOp, nameOp);
	return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime()).get(queryURI);
    }

    @Override
    public final Response searchAsResponse(final Long id, final String name) {
	final String queryURI = getURI() + "?q=" + SearchTestUtil.constructQueryString(id, name);
	return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime()).get(queryURI);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final List<T> search(final Long id, final String name) {
	final String queryURI = getURI() + "?q=" + SearchTestUtil.constructQueryString(id, name);
	final Response searchResponse = givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime()).get(queryURI);
	Preconditions.checkState(searchResponse.getStatusCode() == 200);

	return getMarshaller().<List> decode(searchResponse.getBody().asString(), List.class);
    }

    // entity (non REST)

    @Override
    public void invalidate(final T entity) {
	throw new UnsupportedOperationException();
    }

    // util

    protected final String findOneAsMime(final String uriOfResource) {
	return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime()).get(uriOfResource).asString();
    }

    //

    public final String getMime() {
	return marshaller.getMime();
    }

    @Override
    public final IMarshaller getMarshaller() {
	return marshaller;
    }

}