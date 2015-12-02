package org.baeldung.um.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.baeldung.common.util.QueryConstants;
import org.baeldung.common.web.controller.AbstractController;
import org.baeldung.common.web.controller.ISortingController;
import org.baeldung.um.service.IUserService;
import org.baeldung.um.util.Um.Privileges;
import org.baeldung.um.util.UmMappings;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = UmMappings.USERS)
public class UserController extends AbstractController<UserDto, UserDto> implements ISortingController<UserDto> {

    @Autowired
    private IUserService service;

    public UserController() {
        super(UserDto.class);
    }

    // API

    // search

    @RequestMapping(params = { QueryConstants.Q_PARAM }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<UserDto> searchAll(@RequestParam(QueryConstants.Q_PARAM) final String queryString) {
        return searchAllInternal(queryString);
    }

    @RequestMapping(params = { QueryConstants.Q_PARAM, QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<UserDto> searchAllPaginated(@RequestParam(QueryConstants.Q_PARAM) final String queryString, @RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return searchAllPaginatedInternal(queryString, page, size);
    }

    // find - all/paginated

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<UserDto> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<UserDto> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, null, null, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<UserDto> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<UserDto> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    // find - one

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public UserDto findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    // create

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid final UserDto resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }

    // update

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Privileges.CAN_USER_WRITE)
    public void update(@PathVariable("id") final Long id, @RequestBody @Valid final UserDto resource) {
        updateInternal(id, resource);
    }

    // delete

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Privileges.CAN_USER_WRITE)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IUserService getService() {
        return service;
    }

}
