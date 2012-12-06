package org.rest.common.client.template;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.security.ClientAuthenticator;
import org.rest.common.client.web.HeaderUtil;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.search.SearchUriBuilder;
import org.rest.common.util.QueryConstants;
import org.rest.common.web.WebConstants;
import org.rest.common.web.util.UriUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractRawClientRestTemplate<T extends IEntity> implements IRawClientTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> clazz;

    @Autowired
    protected IMarshaller marshaller;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ClientAuthenticator auth;

    public AbstractRawClientRestTemplate(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

    // find - one

    @Override
    public final T findOne(final long id, final Pair<String, String> credentials) {
        if (credentials != null) {
            auth.givenAuthenticated(restTemplate, credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated();
        }

        try {
            final ResponseEntity<T> response = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.GET, findRequestEntity(), clazz);
            return response.getBody();
        } catch (final HttpClientErrorException clientEx) {
            return null;
        }
    }

    @Override
    public final T findOne(final long id) {
        try {
            final ResponseEntity<T> response = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.GET, findRequestEntity(), clazz);
            return response.getBody();
        } catch (final HttpClientErrorException clientEx) {
            return null;
        }
    }

    @Override
    public final T findOneByUri(final String uri, final Pair<String, String> credentials) {
        final ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), clazz);
        return response.getBody();
    }

    // find - all

    @Override
    public final List<T> findAll() {
        beforeReadOperation();
        final ResponseEntity<String> findAllResponse = restTemplate.exchange(getUri(), HttpMethod.GET, findRequestEntity(), String.class);
        final String body = findAllResponse.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return marshaller.decodeList(body, clazz);
    }

    @Override
    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        beforeReadOperation();
        final String uri = getUri() + QueryConstants.Q_SORT_BY + sortBy + QueryConstants.S_ORDER + sortOrder;
        final ResponseEntity<String> findAllResponse = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), String.class);
        final String body = findAllResponse.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return marshaller.decodeList(body, clazz);
    }

    @Override
    public final List<T> findAllPaginated(final int page, final int size) {
        beforeReadOperation();
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        final ResponseEntity<List> findAllResponse = restTemplate.exchange(uri.toString(), HttpMethod.GET, findRequestEntity(), List.class);
        final List<T> body = findAllResponse.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return body;
    }

    @Override
    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final ResponseEntity<String> allPaginatedAndSortedAsResponse = findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder);
        final String bodyAsString = allPaginatedAndSortedAsResponse.getBody();
        if (bodyAsString == null) {
            return Lists.newArrayList();
        }
        return marshaller.decodeList(bodyAsString, clazz);
    }

    @Override
    public final List<T> findAllByUri(final String uri, final Pair<String, String> credentials) {
        final ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), List.class);
        final List<T> body = response.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return body;
    }

    // as response

    final ResponseEntity<String> findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder) {
        beforeReadOperation();
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

        return restTemplate.exchange(uri.toString(), HttpMethod.GET, findRequestEntity(), String.class);
    }

    protected final ResponseEntity<List> findAllAsResponse(final String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), List.class);
    }

    // create

    @Override
    public final T create(final T resource, final Pair<String, String> credentials) {
        final String locationOfCreatedResource = createAsUri(resource, credentials);

        return findOneByUri(locationOfCreatedResource, credentials);
    }

    @Override
    public final T create(final T resource) {
        final String locationOfCreatedResource = createAsUri(resource, null);

        return findOneByUri(locationOfCreatedResource, null);
    }

    @Override
    public final String createAsUri(final T resource, final Pair<String, String> credentials) {
        if (credentials != null) {
            auth.givenAuthenticated(restTemplate, credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated();
        }
        final ResponseEntity<Void> responseEntity = restTemplate.exchange(getUri(), HttpMethod.POST, new HttpEntity<T>(resource, writeHeaders()), Void.class);

        final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
        Preconditions.checkNotNull(locationOfCreatedResource);

        return locationOfCreatedResource;
    }

    // update

    @Override
    public final void update(final T resource) {
        givenAuthenticated();
        final ResponseEntity<T> responseEntity = restTemplate.exchange(getUri() + "/" + resource.getId(), HttpMethod.PUT, new HttpEntity<T>(resource, writeHeaders()), clazz);
        Preconditions.checkState(responseEntity.getStatusCode().value() == 200);
    }

    // delete

    @Override
    public final void delete(final long id) {
        // final ResponseEntity<Object> deleteResourceResponse = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.DELETE, new HttpEntity<T>(writeHeaders()), null);
        final ResponseEntity<Void> deleteResourceResponse = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.DELETE, new HttpEntity<Void>(writeHeaders()), Void.class);

        Preconditions.checkState(deleteResourceResponse.getStatusCode().value() == 204);
    }

    @Override
    public final void deleteAll() {
        throw new UnsupportedOperationException();
    }

    // search

    @Override
    public final List<T> searchAll(final Triple<String, ClientOperation, String>... constraints) {
        beforeReadOperation();
        final SearchUriBuilder builder = new SearchUriBuilder();
        for (final Triple<String, ClientOperation, String> constraint : constraints) {
            builder.consume(constraint);
        }

        final URI uri = UriUtil.createSearchUri(getUri() + QueryConstants.QUERY_PREFIX + "{qu}", builder.build());
        final ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), String.class);
        Preconditions.checkState(response.getStatusCode().value() == 200);

        return marshaller.decodeList(response.getBody(), clazz);
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

    // count

    @Override
    public final long count() {
        final ResponseEntity<Long> countResourceResponse = restTemplate.exchange(getUri() + "/count", HttpMethod.GET, null, Long.class);
        Preconditions.checkState(countResourceResponse.getStatusCode().value() == 200);
        return countResourceResponse.getBody();
    }

    // util

    protected final HttpEntity<Void> findRequestEntity() {
        return new HttpEntity<Void>(findHeaders());
    }

    // template method

    public abstract Pair<String, String> getDefaultCredentials();

    public final void givenAuthenticated() {
        final Pair<String, String> defaultCredentials = getDefaultCredentials();
        auth.givenAuthenticated(restTemplate, defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    /**
     * - this is a hook that executes before read operations, in order to allow custom security work to happen for read operations; similar to: AbstractRestTemplate.findRequest
     */
    protected void beforeReadOperation() {
        //
    }

    /**
     * - note: hook to be able to customize the find headers if needed
     */
    protected HttpHeaders findHeaders() {
        return HeaderUtil.createAcceptHeaders(marshaller);
    }

    /**
     * - note: hook to be able to customize the write headers if needed
     */
    protected HttpHeaders writeHeaders() {
        return HeaderUtil.createContentTypeHeaders(marshaller);
    }

}
