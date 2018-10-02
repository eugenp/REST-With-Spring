package com.baeldung.um.web.controller;

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

import com.baeldung.common.util.QueryConstants;
import com.baeldung.common.web.controller.AbstractController;
import com.baeldung.common.web.controller.ISortingController;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.service.IPrivilegeService;
import com.baeldung.um.util.UmMappings;

@RestController
@RequestMapping(UmMappings.PRIVILEGES)
public class PrivilegeController extends AbstractController<Privilege> implements ISortingController<Privilege> {

    @Autowired
    private IPrivilegeService service;
    
    // API

    // find - all/paginated

    @Override
    @GetMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY })
    public List<Privilege> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @Override
    @GetMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE })
    public List<Privilege> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return findPaginatedInternal(page, size);
    }

    @Override
    @GetMapping(params = { QueryConstants.SORT_BY })
    public List<Privilege> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @GetMapping
    public List<Privilege> findAll(final HttpServletRequest request) {
        return findAllInternal(request);
    }

    // find - one

    @GetMapping("/{id}")
    public Privilege findOne(@PathVariable("id") final Long id) {
        return findOneInternal(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final Privilege resource) {
        createInternal(resource);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @RequestBody final Privilege resource) {
        updateInternal(id, resource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IPrivilegeService getService() {
        return service;
    }
}