package com.baeldung.common.web.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;

import com.baeldung.common.persistence.model.IEntity;

import reactor.core.publisher.Flux;

public interface ISortingController<T extends IEntity> {

    public Flux<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

    public Flux<T> findAllPaginated(final int page, final int size);

    public Flux<T> findAllSorted(final String sortBy, final String sortOrder);

    public Flux<T> findAll(final ServerHttpRequest request);

}
