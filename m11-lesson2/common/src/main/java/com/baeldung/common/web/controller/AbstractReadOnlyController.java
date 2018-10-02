package com.baeldung.common.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.common.interfaces.IWithName;
import com.baeldung.common.persistence.service.IRawService;
import com.baeldung.common.web.RestPreconditions;
import com.baeldung.common.web.exception.MyResourceNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractReadOnlyController<T extends IWithName> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // find - one

    protected final Mono<T> findOneInternal(final Long id) {
        return Mono.just(RestPreconditions.checkNotNull(getService().findOne(id)));
    }

    // find - all

    protected final Flux<T> findAllInternal(final ServerHttpRequest request) {
        if (!request.getQueryParams().isEmpty()) {
            throw new MyResourceNotFoundException();
        }

        return Flux.fromStream(getService().findAll().stream());
    }

    protected final Flux<T> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<T> resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }

        return Flux.fromStream(resultPage.getContent().stream());
    }

    protected final Flux<T> findPaginatedInternal(final int page, final int size) {
        final Page<T> resultPage = getService().findAllPaginatedRaw(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }

        return Flux.fromStream(resultPage.getContent().stream());
    }

    protected final Flux<T> findAllSortedInternal(final String sortBy, final String sortOrder) {
        final List<T> resultPage = getService().findAllSorted(sortBy, sortOrder);
        return Flux.fromStream(resultPage.stream());
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
     * 
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
