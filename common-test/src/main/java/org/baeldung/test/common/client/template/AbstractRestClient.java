package org.baeldung.test.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.http.HttpHeaders;
import org.baeldung.client.marshall.IMarshaller;
import org.baeldung.client.util.SearchUriBuilder;
import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.search.ClientOperation;
import org.baeldung.common.util.QueryConstants;
import org.baeldung.common.web.WebConstants;
import org.baeldung.test.common.client.security.ITestAuthenticator;
import org.baeldung.test.common.search.SearchTestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractRestClient<T extends IDto> implements IRestTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected IMarshaller marshaller;

    @Autowired
    protected ITestAuthenticator auth;

    protected final Class<T> clazz;

    public AbstractRestClient(final Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        this.clazz = clazz;
    }

    // find - one

    @Override
    public final T findOne(final long id) {
        final String uriOfResource = getUri() + WebConstants.PATH_SEP + id;
        return findOneByUri(uriOfResource);
    }

    public final Response findOneAsResponse(final long id) {
        return findOneAsResponse(id, null);
    }

    @Override
    public final Response findOneAsResponse(final long id, final RequestSpecification req) {
        final String uriOfResource = getUri() + WebConstants.PATH_SEP + id;
        return findOneByUriAsResponse(uriOfResource, req);
    }

    public final T findOneByUri(final String uriOfResource) {
        final String resourceAsMime = findOneByUriAsString(uriOfResource);
        return marshaller.decode(resourceAsMime, clazz);
    }

    @Override
    public final T findOneByUri(final String uriOfResource, final Pair<String, String> credentials) {
        final String resourceAsMime = findOneByUriAsString(uriOfResource);
        return marshaller.decode(resourceAsMime, clazz);
    }

    protected final String findOneByUriAsString(final String uriOfResource) {
        final Response response = findOneByUriAsResponse(uriOfResource);
        Preconditions.checkState(response.getStatusCode() == 200);

        return response.asString();
    }

    protected final String findOneByUriAsString(final String uriOfResource, final RequestSpecification req) {
        final Response response = findOneByUriAsResponse(uriOfResource, req);
        Preconditions.checkState(response.getStatusCode() == 200);

        return response.asString();
    }

    public final Response findOneByUriAsResponse(final String uriOfResource) {
        return findOneByUriAsResponse(uriOfResource, null);
    }

    @Override
    public final Response findOneByUriAsResponse(final String uriOfResource, final RequestSpecification req) {
        if (req == null) {
            return readRequest().get(uriOfResource);
        }
        return readRequest(req).get(uriOfResource);
    }

    @Override
    public final Response findAllByUriAsResponse(final String uriOfResource, final RequestSpecification req) {
        if (req == null) {
            return readExtendedRequest().get(uriOfResource);
        }
        return readExtendedRequest(req).get(uriOfResource);
    }

    public final Response findAllByUriAsResponse(final String uriOfResource) {
        return findAllByUriAsResponse(uriOfResource, null);
    }

    // find - all

    @Override
    public List<T> findAll() {
        return findAllByUri(getUri());
    }

    public final List<T> findAllByUri(final String uri) {
        return findAllByUri(uri, null);
    }

    @Override
    public final List<T> findAllByUri(final String uri, final Pair<String, String> credentials) {
        final Response allAsResponse = readExtendedRequest().get(uri);
        final List<T> listOfResources = marshaller.<T> decodeList(allAsResponse.getBody().asString(), clazz);
        if (listOfResources == null) {
            return Lists.newArrayList();
        }
        return listOfResources;
    }

    public final Response findAllAsResponse() {
        return findAllAsResponse(null);
    }

    @Override
    public final Response findAllAsResponse(final RequestSpecification req) {
        return findAllByUriAsResponse(getUri(), req);
    }

    // find - all (sorted, paginated)

    @Override
    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Response findAllResponse = findAllByUriAsResponse(getUri() + QueryConstants.Q_SORT_BY + sortBy + QueryConstants.S_ORDER + sortOrder);
        return marshaller.<T> decodeList(findAllResponse.getBody().asString(), clazz);
    }

    @Override
    public final List<T> findAllPaginated(final int page, final int size) {
        final Response allPaginatedAsResponse = findAllPaginatedAsResponse(page, size);
        return getMarshaller().decodeList(allPaginatedAsResponse.asString(), clazz);
    }

    @Override
    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Response allPaginatedAndSortedAsResponse = findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder);
        return getMarshaller().decodeList(allPaginatedAndSortedAsResponse.asString(), clazz);
    }

    public final Response findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder) {
        return findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder, null);
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

    public final Response findAllSortedAsResponse(final String sortBy, final String sortOrder) {
        return findAllSortedAsResponse(sortBy, sortOrder, null);
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

    public final Response findAllPaginatedAsResponse(final int page, final int size) {
        return findAllPaginatedAsResponse(page, size, null);
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
        final String uriForResourceCreation = createAsUri(resource);
        final String resourceAsMime = findOneByUriAsString(uriForResourceCreation);

        return marshaller.decode(resourceAsMime, clazz);
    }

    @Override
    public final String createAsUri(final T resource) {
        return createAsUri(resource, null);
    }

    @Override
    public final String createAsUri(final T resource, final Pair<String, String> credentials) {
        final Response response = createAsResponse(resource, credentials);
        Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

        final String locationOfCreatedResource = response.getHeader(HttpHeaders.LOCATION);
        Preconditions.checkNotNull(locationOfCreatedResource);
        return locationOfCreatedResource;
    }

    @Override
    public final Response createAsResponse(final T resource) {
        return createAsResponse(resource, null);
    }

    @Override
    public final Response createAsResponse(final T resource, final Pair<String, String> credentials) {
        Preconditions.checkNotNull(resource);
        RequestSpecification givenAuthenticated = null;
        if (credentials != null) {
            givenAuthenticated = auth.givenBasicAuthenticated(credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated = givenWriteAuthenticated();
        }

        final String resourceAsString = marshaller.encode(resource);
        logger.debug("Creating Resource against URI: " + getUri());
        return givenAuthenticated.contentType(marshaller.getMime()).body(resourceAsString).post(getUri());
    }

    // update

    @Override
    public final void update(final T resource) {
        final Response updateResponse = updateAsResponse(resource);
        Preconditions.checkState(updateResponse.getStatusCode() == 200, "Update Operation: " + updateResponse.getStatusCode());
    }

    @Override
    public final Response updateAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);

        final String resourceAsString = marshaller.encode(resource);
        return givenWriteAuthenticated().contentType(marshaller.getMime()).body(resourceAsString).put(getUri() + "/" + resource.getId());
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
        return givenDeleteAuthenticated().delete(uriOfResource);
    }

    // search - as response

    @Override
    public final Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp) {
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp);
        return readExtendedRequest().get(queryURI);
    }

    @Override
    public final Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size) {
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp) + "&page=" + page + "&size=" + size;
        return readExtendedRequest().get(queryURI);
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

        final Response searchResponse = readExtendedRequest().get(queryURI);
        Preconditions.checkState(searchResponse.getStatusCode() == 200, "Search is = " + searchResponse.getStatusCode());

        return searchResponse;
    }

    @Override
    public final List<T> searchPaginated(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size) {
        final String queryURI = getUri() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp) + "&page=" + page + "&size=" + size;
        final Response searchResponse = readExtendedRequest().get(queryURI);
        Preconditions.checkState(searchResponse.getStatusCode() == 200, "Search is = " + searchResponse.getStatusCode());

        return getMarshaller().<List> decode(searchResponse.getBody().asString(), List.class);
    }

    // count

    @Override
    public long count() {
        return Long.valueOf(countAsResponse().asString());
    }

    @Override
    public final Response countAsResponse() {
        return givenReadAuthenticated().get(getUri() + "/count");
    }

    // API - other

    public final Response read(final String uriOfResource) {
        return readRequest().get(uriOfResource);
    }

    private final RequestSpecification readRequest(final RequestSpecification req) {
        return req.header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    protected final RequestSpecification readRequest() {
        return givenReadAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    protected final RequestSpecification readExtendedRequest(final RequestSpecification req) {
        return req.header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    protected final RequestSpecification readExtendedRequest() {
        return givenReadExtendedAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    // security

    @Override
    public final RequestSpecification givenReadAuthenticated() {
        final Pair<String, String> credentials = getReadCredentials();
        return auth.givenBasicAuthenticated(credentials.getLeft(), credentials.getRight());
    }

    final RequestSpecification givenReadExtendedAuthenticated() {
        final Pair<String, String> credentials = getReadExtendedCredentials();
        return auth.givenBasicAuthenticated(credentials.getLeft(), credentials.getRight());
    }

    final RequestSpecification givenWriteAuthenticated() {
        final Pair<String, String> credentials = getWriteCredentials();
        return auth.givenBasicAuthenticated(credentials.getLeft(), credentials.getRight());
    }

    final RequestSpecification givenDeleteAuthenticated() {
        final Pair<String, String> credentials = getWriteCredentials();
        return auth.givenBasicAuthenticated(credentials.getLeft(), credentials.getRight());
    }

    protected Pair<String, String> getWriteCredentials() {
        return getDefaultCredentials();
    }

    protected Pair<String, String> getDeleteCredentials() {
        return getWriteCredentials();
    }

    @Override
    public Pair<String, String> getReadCredentials() {
        return getDefaultCredentials();
    }

    protected Pair<String, String> getReadExtendedCredentials() {
        return getReadCredentials();
    }

    protected abstract Pair<String, String> getDefaultCredentials();

    /**
     * - this is a hook that executes before read operations, in order to allow custom security work to happen for read operations; similar to: AbstractRestTemplate.findRequest
     */
    protected void beforeReadOperation() {
        //
    }

    //

    @Override
    public final IMarshaller getMarshaller() {
        return marshaller;
    }

}
