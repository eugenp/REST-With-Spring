package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.http.HttpHeaders;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.security.ITestAuthenticator;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.search.SearchUriBuilder;
import org.rest.common.sec.util.SearchTestUtil;
import org.rest.common.util.QueryConstants;
import org.rest.common.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * REST Template for the consumption of the REST API <br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractTestRestTemplate<T extends IEntity> implements IRestTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Class<T> clazz;

    @Autowired
    protected IMarshaller marshaller;

    @Autowired
    protected ITestAuthenticator auth;

    public AbstractTestRestTemplate(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // find - one

    @Override
    public final T findOne(final long id) {
        final String uriOfResource = getUri() + WebConstants.PATH_SEP + id;
        return findOneByUri(uriOfResource, null);
    }

    @Override
    public final T findOneByUri(final String uriOfResource, final Pair<String, String> credentials) {
        final String resourceAsMime = findOneByUriAsString(uriOfResource);
        return marshaller.decode(resourceAsMime, clazz);
    }

    protected final String findOneByUriAsString(final String uriOfResource) {
        final Response response = findOneByUriAsResponse(uriOfResource, null);
        Preconditions.checkState(response.getStatusCode() == 200);

        return response.asString();
    }

    protected final String findOneByUriAsString(final String uriOfResource, final RequestSpecification req) {
        final Response response = findOneByUriAsResponse(uriOfResource, req);
        Preconditions.checkState(response.getStatusCode() == 200);

        return response.asString();
    }

    @Override
    public final Response findOneByUriAsResponse(final String uriOfResource, final RequestSpecification req) {
        if (req == null) {
            return findOneRequest().get(uriOfResource);
        }
        return findOneRequest(req).get(uriOfResource);
    }

    @Override
    public final Response findAllByUriAsResponse(final String uriOfResource, final RequestSpecification req) {
        if (req == null) {
            return findAllRequest().get(uriOfResource);
        }
        return findAllRequest(req).get(uriOfResource);
    }

    // find - all

    @Override
    public List<T> findAll() {
        return findAllByUri(getUri(), null);
    }

    @Override
    public final List<T> findAllByUri(final String uri, final Pair<String, String> credentials) {
        final Response allAsResponse = findAllRequest().get(uri);
        final List<T> listOfResources = marshaller.<T> decodeList(allAsResponse.getBody().asString(), clazz);
        if (listOfResources == null) {
            return Lists.newArrayList();
        }
        return listOfResources;
    }

    @Override
    public final Response findAllAsResponse(final RequestSpecification req) {
        return findAllByUriAsResponse(getUri(), req);
    }

    // find - all (sorted, paginated)

    @Override
    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Response findAllResponse = findAllByUriAsResponse(getUri() + QueryConstants.Q_SORT_BY + sortBy + QueryConstants.S_ORDER + sortOrder, null);
        return marshaller.<T> decodeList(findAllResponse.getBody().asString(), clazz);
    }

    @Override
    public final List<T> findAllPaginated(final int page, final int size) {
        final Response allPaginatedAsResponse = findAllPaginatedAsResponse(page, size, null);
        return getMarshaller().decodeList(allPaginatedAsResponse.asString(), clazz);
    }

    @Override
    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Response allPaginatedAndSortedAsResponse = findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder, null);
        return getMarshaller().decodeList(allPaginatedAndSortedAsResponse.asString(), clazz);
    }

    @Override
    public final Response findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder, final RequestSpecification req) {
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return findAllByUriAsResponse(uri.toString(), req);
    }

    @Override
    public final Response findAllSortedAsResponse(final String sortBy, final String sortOrder, final RequestSpecification req) {
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return findAllByUriAsResponse(uri.toString(), req);
    }

    @Override
    public final Response findAllPaginatedAsResponse(final int page, final int size, final RequestSpecification req) {
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        return findAllByUriAsResponse(uri.toString(), req);
    }

    // create

    @Override
    public final T create(final T resource) {
        final String uriForResourceCreation = createAsUri(resource, null);
        final String resourceAsMime = findOneByUriAsString(uriForResourceCreation);

        return marshaller.decode(resourceAsMime, clazz);
    }

    @Override
    public final String createAsUri(final T resource, final Pair<String, String> credentials) {
        Preconditions.checkNotNull(resource);
        RequestSpecification givenAuthenticated = null;
        if (credentials != null) {
            givenAuthenticated = auth.givenBasicAuthenticated(credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated = givenAuthenticated();
        }

        final String resourceAsString = marshaller.encode(resource);
        final Response response = givenAuthenticated.contentType(marshaller.getMime()).body(resourceAsString).post(getUri());
        Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

        final String locationOfCreatedResource = response.getHeader(HttpHeaders.LOCATION);
        Preconditions.checkNotNull(locationOfCreatedResource);
        return locationOfCreatedResource;
    }

    @Override
    public final Response createAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);

        final String resourceAsString = marshaller.encode(resource);
        logger.debug("Creating Resource against URI: " + getUri());
        return givenAuthenticated().contentType(marshaller.getMime()).body(resourceAsString).post(getUri());
    }

    // update

    @Override
    public final void update(final T resource) {
        final Response updateResponse = updateAsResponse(resource);
        Preconditions.checkState(updateResponse.getStatusCode() == 200, "update operation: " + updateResponse.getStatusCode());
    }

    @Override
    public final Response updateAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);

        final String resourceAsString = marshaller.encode(resource);
        return givenAuthenticated().contentType(marshaller.getMime()).body(resourceAsString).put(getUri() + "/" + resource.getId());
    }

    // delete

    @Override
    public final void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void delete(final long id) {
        final Response deleteResponse = deleteAsResponse(getUri() + WebConstants.PATH_SEP + id);
        Preconditions.checkState(deleteResponse.getStatusCode() == 204);
    }

    @Override
    public final Response deleteAsResponse(final String uriOfResource) {
        return givenAuthenticated().delete(uriOfResource);
    }

    // search - as response

    @Override
    public final Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp) {
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp);
        return findAllRequest().get(queryURI);
    }

    @Override
    public final Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size) {
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp) + "&page=" + page + "&size=" + size;
        return findAllRequest().get(queryURI);
    }

    // search

    @Override
    public final List<T> searchAll(final Triple<String, ClientOperation, String>... constraints) {
        final Response searchResponse = searchAsResponse(constraints);

        return getMarshaller().<T> decodeList(searchResponse.getBody().asString(), clazz);
    }

    @Override
    public final T searchOne(final Triple<String, ClientOperation, String>... constraints) {
        final List<T> all = searchAll(constraints);
        if (all.isEmpty()) {
            return null;
        }
        Preconditions.checkState(all.size() <= 1);
        return all.get(0);
    }

    @Override
    public final Response searchAsResponse(final Triple<String, ClientOperation, String>... constraints) {
        final SearchUriBuilder builder = new SearchUriBuilder();
        for (final Triple<String, ClientOperation, String> constraint : constraints) {
            builder.consume(constraint);
        }
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + builder.build();

        final Response searchResponse = findAllRequest().get(queryURI);
        Preconditions.checkState(searchResponse.getStatusCode() == 200);

        return searchResponse;
    }

    @Override
    public final List<T> searchPaginated(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size) {
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp) + "&page=" + page + "&size=" + size;
        final Response searchResponse = findAllRequest().get(queryURI);
        Preconditions.checkState(searchResponse.getStatusCode() == 200);

        return getMarshaller().<List> decode(searchResponse.getBody().asString(), List.class);
    }

    // count

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    // util

    protected RequestSpecification findAllRequest() {
        return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    protected RequestSpecification findAllRequest(final RequestSpecification req) {
        return req.header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    protected RequestSpecification findOneRequest() {
        return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    protected RequestSpecification findOneRequest(final RequestSpecification req) {
        return req.header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    // security

    @Override
    public RequestSpecification givenAuthenticated() {
        final Pair<String, String> defaultCredentials = getDefaultCredentials();
        return auth.givenBasicAuthenticated(defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    public abstract Pair<String, String> getDefaultCredentials();

    //

    @Override
    public final IMarshaller getMarshaller() {
        return marshaller;
    }

}
