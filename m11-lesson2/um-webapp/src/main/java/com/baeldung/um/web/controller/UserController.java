package com.baeldung.um.web.controller;

import com.baeldung.common.web.controller.AbstractController;
import com.baeldung.common.web.controller.ISortingController;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.service.IUserService;
import com.baeldung.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.baeldung.common.util.QueryConstants.PAGE;
import static com.baeldung.common.util.QueryConstants.SIZE;
import static com.baeldung.common.util.QueryConstants.SORT_BY;
import static com.baeldung.common.util.QueryConstants.SORT_ORDER;

@RestController
@RequestMapping(UmMappings.USERS)
public class UserController extends AbstractController<User> implements ISortingController<User> {

    @Autowired
    private IUserService service;

    // API

    // find - all/paginated

    @Override
    @GetMapping(params = { PAGE, SIZE, SORT_BY })
    public Flux<User> findAllPaginatedAndSorted(@RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(SORT_BY) String sortBy, @RequestParam(SORT_ORDER) String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @Override
    @GetMapping(params = { PAGE, SIZE })
    public Flux<User> findAllPaginated(@RequestParam(PAGE) int page, @RequestParam(SIZE) int size) {
        return findPaginatedInternal(page, size);
    }

    @Override
    @GetMapping(params = { SORT_BY })
    public Flux<User> findAllSorted(@RequestParam(SORT_BY) String sortBy, @RequestParam(SORT_ORDER) String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @GetMapping
    public Flux<User> findAll(ServerHttpRequest request) {
        return findAllInternal(request);
    }

    @GetMapping("/{id}")
    public Mono<User> findOne(@PathVariable("id") Long id) {
        return findOneInternal(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody User resource) {
        return createInternal(resource);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody User resource) {
        updateInternal(id, resource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected IUserService getService() {
        return service;
    }
}
