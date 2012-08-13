package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.http.HttpHeaders;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.security.IClientAuthenticationComponent;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.search.SearchUriBuilder;
import org.rest.common.sec.util.SearchTestUtil;
import org.rest.common.util.QueryConstants;
import org.rest.common.util.SearchCommonUtil;
import org.rest.common.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * REST Template for the consumption of the REST API <br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractRESTTemplate<T extends IEntity> implements IRESTTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("jacksonMarshaller")
    protected IMarshaller marshaller;

    @Autowired
    protected IClientAuthenticationComponent auth;

    protected final Class<T> clazz;

    public AbstractRESTTemplate(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // find - one

    @Override
    public T findOne(final long id) {
        final String resourceAsMime = findOneAsMime(getURI() + WebConstants.PATH_SEP + id);
        if (resourceAsMime == null) {
            return null;
        }
        return marshaller.decode(resourceAsMime, clazz);
    }

    @Override
    public Response findOneAsResponse(final String uriOfResource) {
        return findOneRequest().get(uriOfResource);
    }

    protected RequestSpecification findOneRequest() {
        return givenAuthenticated().header(HttpHeaders.ACCEPT, marshaller.getMime());
    }

    // find - all

    @Override
    public List<T> findAll() {
        final Response findAllResponse = findOneAsResponse(getURI());
        return marshaller.<T> decodeList(findAllResponse.getBody().asString(), clazz);
    }

    @Override
    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Response findAllResponse = findOneAsResponse(getURI() + QueryConstants.Q_SORT_BY + sortBy + QueryConstants.S_ORDER + sortOrder);
        return marshaller.<T> decodeList(findAllResponse.getBody().asString(), clazz);
    }

    @Override
    public Response findAllAsResponse() {
        return findOneAsResponse(getURI());
    }

    @Override
    public Response findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder) {
        final StringBuilder uri = new StringBuilder(getURI());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(SearchCommonUtil.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(SearchCommonUtil.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(SearchCommonUtil.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return findOneAsResponse(uri.toString());
    }

    @Override
    public Response findAllSortedAsResponse(final String sortBy, final String sortOrder) {
        final StringBuilder uri = new StringBuilder(getURI());
        uri.append(QueryConstants.QUESTIONMARK);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(SearchCommonUtil.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return findOneAsResponse(uri.toString());
    }

    @Override
    public Response findAllPaginatedAsResponse(final int page, final int size) {
        final StringBuilder uri = new StringBuilder(getURI());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(SearchCommonUtil.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        return findOneAsResponse(uri.toString());
    }

    // create

    @Override
    public T create(final T resource) {
        final String uriForResourceCreation = createAsURI(resource);
        final String resourceAsXML = findOneAsMime(uriForResourceCreation);

        return marshaller.decode(resourceAsXML, clazz);
    }

    @Override
    public String createAsURI(final T resource) {
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
        final Response updateResponse = updateAsResponse(resource);
        Preconditions.checkState(updateResponse.getStatusCode() == 200, "update operation: " + updateResponse.getStatusCode());
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
        final Response deleteResponse = deleteAsResponse(getURI() + WebConstants.PATH_SEP + id);
        Preconditions.checkState(deleteResponse.getStatusCode() == 204);
    }

    @Override
    public Response deleteAsResponse(final String uriOfResource) {
        return givenAuthenticated().delete(uriOfResource);
    }

    // search - as response

    @Override
    public Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp) {
        final String queryURI = getURI() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp);
        return findOneRequest().get(queryURI);
    }

    @Override
    public Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size) {
        final String queryURI = getURI() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp) + "&page=" + page + "&size=" + size;
        return findOneRequest().get(queryURI);
    }

    // search

    @Override
    public List<T> search(final Triple<String, ClientOperation, String>... constraints) {
        final Response searchResponse = searchAsResponse(constraints);

        return getMarshaller().<T> decodeList(searchResponse.getBody().asString(), clazz);
    }

    @Override
    public Response searchAsResponse(final Triple<String, ClientOperation, String>... constraints) {
        final SearchUriBuilder builder = new SearchUriBuilder();
        for (final Triple<String, ClientOperation, String> constraint : constraints) {
            builder.consume(constraint);
        }
        final String queryURI = getURI() + QueryConstants.QUERY_PREFIX + builder.build();

        final Response searchResponse = findOneRequest().get(queryURI);
        Preconditions.checkState(searchResponse.getStatusCode() == 200);

        return searchResponse;
    }

    @Override
    public List<T> searchPaged(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size) {
        final String queryURI = getURI() + QueryConstants.QUERY_PREFIX + SearchTestUtil.constructQueryString(idOp, nameOp) + "&page=" + page + "&size=" + size;
        final Response searchResponse = findOneRequest().get(queryURI);
        Preconditions.checkState(searchResponse.getStatusCode() == 200);

        return getMarshaller().<List> decode(searchResponse.getBody().asString(), List.class);
    }

    // count

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    // entity (non REST)

    @Override
    public void invalidate(final T entity) {
        throw new UnsupportedOperationException();
    }

    // util

    protected String findOneAsMime(final String uriOfResource) {
        final Response response = findOneRequest().get(uriOfResource);
        if (response.getStatusCode() != 200) {
            return null;
        }
        return response.asString();
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
