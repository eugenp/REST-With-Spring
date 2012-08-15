package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.QueryConstants;
import org.rest.common.util.SearchCommonUtil;
import org.rest.common.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;

@SuppressWarnings("rawtypes")
public abstract class AbstractClientRESTTemplate<T extends INameableEntity> implements IClientTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> clazz;

    @Autowired
    @Qualifier("jacksonMarshaller")
    protected IMarshaller marshaller;

    @Autowired
    protected RestTemplate restTemplate;

    public AbstractClientRESTTemplate(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

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
    public final T findByName(final String name) {
        return findOneByAttributes("name", name);
    }

    @Override
    public final T findOneByAttributes(final String... attributes) {
        final List<T> resourcesByName = findAllByURI(getURI() + QueryConstants.QUERY_PREFIX + SearchCommonUtil.constructURI(attributes));
        if (resourcesByName.isEmpty()) {
            return null;
        }
        Preconditions.checkState(resourcesByName.size() <= 1);
        return resourcesByName.get(0);
    }

    // find - all

    @Override
    public final List<T> findAll() {
        final ResponseEntity<String> findAllResponse = restTemplate.exchange(getURI(), HttpMethod.GET, findRequestEntity(), String.class);
        return marshaller.decodeList(findAllResponse.getBody(), clazz);
    }

    @Override
    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final ResponseEntity<String> allPaginatedAndSortedAsResponse = findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder);
        return marshaller.decodeList(allPaginatedAndSortedAsResponse.getBody(), clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final ResponseEntity<List> findAllResponse = restTemplate.exchange(getURI() + QueryConstants.Q_SORT_BY + sortBy, HttpMethod.GET, findRequestEntity(), List.class);
        return findAllResponse.getBody();
    }

    @Override
    public final List<T> findAllPaginated(final int page, final int size) {
        final StringBuilder uri = new StringBuilder(getURI());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(SearchCommonUtil.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        final ResponseEntity<String> findAllResponse = restTemplate.exchange(uri.toString(), HttpMethod.GET, findRequestEntity(), String.class);
        return getMarshaller().decodeList(findAllResponse.getBody(), clazz);
    }

    @Override
    public final List<T> findAllByAttributes(final String... attributes) {
        final List<T> resourcesByAttributes = findAllByURI(getURI() + QueryConstants.QUERY_PREFIX + SearchCommonUtil.constructURI(attributes));
        return resourcesByAttributes;
    }

    final ResponseEntity<String> findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder) {
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

        return restTemplate.exchange(uri.toString(), HttpMethod.GET, findRequestEntity(), String.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<T> findAllByURI(final String uri) {
        final ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), List.class);
        return response.getBody();
    }

    // create

    @Override
    public final T create(final T resource) {
        final String locationOfCreatedResource = createAsURI(resource);

        return findOneByURI(locationOfCreatedResource);
    }

    @Override
    public final String createAsURI(final T resource) {
        givenAuthenticated();
        final ResponseEntity<List> responseEntity = restTemplate.exchange(getURI(), HttpMethod.POST, new HttpEntity<T>(resource, createContentTypeHeaders()), List.class);

        final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
        Preconditions.checkNotNull(locationOfCreatedResource);

        return locationOfCreatedResource;
    }

    // update

    @Override
    public final void update(final T resource) {
        givenAuthenticated();
        final ResponseEntity<T> responseEntity = restTemplate.exchange(getURI(), HttpMethod.PUT, new HttpEntity<T>(resource, createContentTypeHeaders()), clazz);
        Preconditions.checkState(responseEntity.getStatusCode().value() == 200);
    }

    // delete

    @Override
    public final void delete(final long id) {
        final ResponseEntity<Object> deleteResourceResponse = restTemplate.exchange(getURI() + WebConstants.PATH_SEP + id, HttpMethod.DELETE, null, null);

        Preconditions.checkState(deleteResourceResponse.getStatusCode().value() == 204);
    }

    // search

    @Override
    public final List<T> search(final Triple<String, ClientOperation, String>... constraints) {
        throw new UnsupportedOperationException();
    }

    // count

    @Override
    public final long count() {
        throw new UnsupportedOperationException();
    }

    // util

    protected final HttpHeaders createGetHeaders() {
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

    protected final HttpEntity<Void> findRequestEntity() {
        return new HttpEntity<Void>(createGetHeaders());
    }

    // template method

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

    public final IMarshaller getMarshaller() {
        return marshaller;
    }

}
