package com.baeldung.common.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.baeldung.common.interfaces.IWithName;
import com.baeldung.common.persistence.service.IRawService;
import com.baeldung.common.util.QueryConstants;
import com.baeldung.common.web.RestPreconditions;
import com.baeldung.common.web.WebConstants;
import com.baeldung.common.web.events.MultipleResourcesRetrievedEvent;
import com.baeldung.common.web.events.PaginatedResultsRetrievedEvent;
import com.baeldung.common.web.events.SingleResourceRetrievedEvent;
import com.baeldung.common.web.exception.MyResourceNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class AbstractReadOnlyController<T extends IWithName> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractReadOnlyController(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // find - one

    protected final T findOneInternal(final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final T resource = findOneInternal(id);
        eventPublisher.publishEvent(new SingleResourceRetrievedEvent<T>(clazz, uriBuilder, response));
        return resource;
    }

    protected final T findOneInternal(final Long id) {
        return RestPreconditions.checkNotNull(getService().findOne(id));
    }

    // find - all

    protected final List<T> findAllInternal(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        if (request.getParameterNames()
            .hasMoreElements()) {
            throw new MyResourceNotFoundException();
        }

        eventPublisher.publishEvent(new MultipleResourcesRetrievedEvent<T>(clazz, uriBuilder, response));
        return getService().findAll();
    }

    protected final void findAllRedirectToPagination(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final String resourceName = clazz.getSimpleName()
            .toString()
            .toLowerCase();
        final String locationValue = uriBuilder.path(WebConstants.PATH_SEP + resourceName)
            .build()
            .encode()
            .toUriString() + QueryConstants.QUESTIONMARK + "page=0&size=10";

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

    protected final List<T> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<T> resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<T>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<T> findPaginatedInternal(final int page, final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<T> resultPage = getService().findAllPaginatedRaw(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<T>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<T> findAllSortedInternal(final String sortBy, final String sortOrder) {
        final List<T> resultPage = getService().findAllSorted(sortBy, sortOrder);
        return resultPage;
    }

    // count

    protected final long countInternal() {
        // InvalidDataAccessApiUsageException dataEx - ResourceNotFoundException
        return getService().count();
    }

    // generic REST operations

    // count

    /**
     * Counts all {@link Privilege} resources in the system
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public long count() {
        return countInternal();
    }

    // template method

    protected abstract IRawService<T> getService();

}
