package com.baeldung.um.web.controller;

import static com.baeldung.common.util.QueryConstants.PAGE;
import static com.baeldung.common.util.QueryConstants.SIZE;
import static com.baeldung.common.util.QueryConstants.SORT_BY;
import static com.baeldung.common.util.QueryConstants.SORT_ORDER;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.baeldung.common.web.controller.AbstractController;
import com.baeldung.common.web.controller.ISortingController;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.service.IUserService;
import com.baeldung.um.util.UmMappings;

@RestController
@RequestMapping(UmMappings.USERS)
public class UserController extends AbstractController<User> implements ISortingController<User> {

    @Autowired
    private IUserService service;

    public UserController() {
        super(User.class);
    }

    // API

    // find - all/paginated

    @Override
    @GetMapping(params = { PAGE, SIZE, SORT_BY })
    public List<User> findAllPaginatedAndSorted(@RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(SORT_BY) String sortBy, @RequestParam(SORT_ORDER) String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @Override
    @GetMapping(params = { PAGE, SIZE })
    public List<User> findAllPaginated(@RequestParam(PAGE) int page, @RequestParam(SIZE) int size) {
        return findPaginatedInternal(page, size);
    }

    @Override
    @GetMapping(params = { SORT_BY })
    public List<User> findAllSorted(@RequestParam(SORT_BY) String sortBy, @RequestParam(SORT_ORDER) String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @GetMapping
    public List<User> findAll(final HttpServletRequest request) {
        return findAllInternal(request);
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return findOneInternal(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User resource) {
        createInternal(resource);
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