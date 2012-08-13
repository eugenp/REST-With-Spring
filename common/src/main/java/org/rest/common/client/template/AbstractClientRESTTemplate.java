package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.QueryConstants;
import org.rest.common.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractClientRESTTemplate<T extends INameableEntity> implements IClientTemplate<T> {

    protected Class<T> clazz;

    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    @Qualifier("jacksonMarshaller")
    protected IMarshaller marshaller;

    public AbstractClientRESTTemplate(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

    // API

    // find - one

    @Override
    public final T findOne(final long id) {
        try {
            final ResponseEntity<T> response = restTemplate.exchange(getURI() + WebConstants.PATH_SEP + id, HttpMethod.GET, findRequestEntity(), clazz);
            return response.getBody();
        } catch (final HttpClientErrorException clientEx) {
            return null;
        }
    }

    @Override
    public final T findOneByURI(final String uri) {
        final ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), clazz);
        return response.getBody();
    }

    // find one - by attributes

    @Override
    public T findByName(final String name) {
        return findOneByAttributes("name", name);
    }

    @Override
    public T findOneByAttributes(final String... attributes) {
        final List<T> resourcesByName = findAllByURI(getURI() + QueryConstants.QUERY_PREFIX + constructURI(attributes));
        if (resourcesByName.isEmpty()) {
            return null;
        }
        Preconditions.checkState(resourcesByName.size() <= 1);
        return resourcesByName.get(0);
    }

    // find - all

    @Override
    public final List<T> findAll() {
        final ResponseEntity<List> findAllResponse = restTemplate.exchange(getURI(), HttpMethod.GET, findRequestEntity(), List.class);
        return findAllResponse.getBody();
    }

    @Override
    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final ResponseEntity<List> findAllResponse = restTemplate.exchange(getURI() + QueryConstants.Q_SORT_BY + sortBy, HttpMethod.GET, findRequestEntity(), List.class);
        return findAllResponse.getBody();
    }

    @Override
    public final List<T> findAllPaginated(final int page, final int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final List<T> findAllByAttributes(final String... attributes) {
        final List<T> resourcesByAttributes = findAllByURI(getURI() + QueryConstants.QUERY_PREFIX + constructURI(attributes));
        return resourcesByAttributes;
    }

    // create

    @Override
    public T create(final T resource) {
        final ResponseEntity responseEntity = restTemplate.exchange(getURI(), HttpMethod.POST, new HttpEntity<T>(resource, createContentTypeHeaders()), Void.class);

        final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
        Preconditions.checkNotNull(locationOfCreatedResource);

        return findOneByURI(locationOfCreatedResource);
    }

    @Override
    public final String createAsURI(final T resource) {
        givenAuthenticated();
        final ResponseEntity responseEntity = restTemplate.exchange(getURI(), HttpMethod.POST, new HttpEntity<T>(resource, createContentTypeHeaders()), List.class);

        final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
        Preconditions.checkNotNull(locationOfCreatedResource);

        return locationOfCreatedResource;
    }

    // update

    @Override
    public final void update(final T resource) {
        final ResponseEntity responseEntity = restTemplate.exchange(getURI(), HttpMethod.PUT, new HttpEntity<T>(resource, createContentTypeHeaders()), clazz);
        Preconditions.checkState(responseEntity.getStatusCode().value() == 200);
    }

    // delete

    @Override
    public final void delete(final long id) {
        final ResponseEntity<Object> deleteResourceResponse = restTemplate.exchange(getURI() + WebConstants.PATH_SEP + id, HttpMethod.DELETE, null, null);

        Preconditions.checkState(deleteResourceResponse.getStatusCode().value() == 204);
    }

    @Override
    public final void deleteAll() {
        throw new UnsupportedOperationException();
    }

    // search

    @Override
    public List<T> search(final Triple<String, ClientOperation, String>... constraints) {
        throw new UnsupportedOperationException();
    }

    // count

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    // util

    final List<T> findAllByURI(final String uri) {
        final ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), List.class);
        return response.getBody();
    }

    protected HttpHeaders createGetHeaders() {
        final HttpHeaders headers = new HttpHeaders() {
            {
                set(com.google.common.net.HttpHeaders.ACCEPT, marshaller.getMime());
            }
        };
        return headers;
    }

    protected final HttpHeaders createContentTypeHeaders() {
        final HttpHeaders headers = new HttpHeaders() {
            {
                set(com.google.common.net.HttpHeaders.CONTENT_TYPE, marshaller.getMime());
            }
        };
        return headers;
    }

    protected static String constructURI(final String... attributes) {
        Preconditions.checkNotNull(attributes);
        Preconditions.checkArgument(attributes.length > 0);
        Preconditions.checkArgument(attributes.length % 2 == 0);

        final UriComponentsBuilder queryParam = UriComponentsBuilder.newInstance();
        for (int i = 0; i <= attributes.length / 2; i += 2) {
            queryParam.queryParam(attributes[i], attributes[i + 1]);
        }

        String query = queryParam.build().encode().getQuery();
        // query = query.replaceAll("=", "%3D");
        return query.replaceAll("&", ",");
    }

    protected HttpEntity<Void> findRequestEntity() {
        return new HttpEntity<Void>(createGetHeaders());
    }

    // template method

    public abstract String getURI();

    @Override
    public final IClientTemplate<T> givenAuthenticated() {
        if (isBasicAuth()) {
            basicAuth();
        } else {
            digestAuth();
        }

        return this;
    }

    protected boolean isBasicAuth() {
        return true;
    }

    protected abstract void basicAuth();

    protected final void digestAuth() {
        throw new UnsupportedOperationException();
    }

}
