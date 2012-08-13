package org.rest.sec.persistence.service.impl.dto;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.search.ClientOperation;
import org.rest.common.web.RestPreconditions;
import org.rest.sec.model.Principal;
import org.rest.sec.model.dto.PrincipalToUserFunction;
import org.rest.sec.model.dto.User;
import org.rest.sec.persistence.service.IPrincipalService;
import org.rest.sec.persistence.service.dto.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IPrincipalService principalService;

    public UserServiceImpl() {
        super();
    }

    // API

    // search

    @Override
    public List<User> search(final Triple<String, ClientOperation, String>... constraints) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<User> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints) {
        throw new UnsupportedOperationException();
    }

    // find - one

    @Override
    public User findByName(final String name) {
        final Principal principal = principalService.findByName(name);
        return new User(principal);
    }

    @Override
    public User findOne(final long id) {
        final Principal principal = principalService.findOne(id);
        if (principal == null) {
            return null;
        }
        return new User(principal);
    }

    // find - many

    @Override
    public List<User> findAll() {
        final List<Principal> allPrincipalEntities = principalService.findAll();
        final List<User> allUsers = Lists.transform(allPrincipalEntities, new PrincipalToUserFunction());

        return allUsers;
    }

    @Override
    public List<User> findAllSorted(final String sortBy, final String sortOrder) {
        final List<Principal> allPrincipalEntitiesSortedAndOrdered = principalService.findAllSorted(sortBy, sortOrder);
        final List<User> allUsers = Lists.transform(allPrincipalEntitiesSortedAndOrdered, new PrincipalToUserFunction());

        return allUsers;
    }

    @Override
    public List<User> findAllPaginated(final int page, final int size) {
        final List<Principal> principalsPaginated = principalService.findAllPaginated(page, size);
        return Lists.transform(principalsPaginated, new PrincipalToUserFunction());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<Principal> principalsPaginatedAndSorted = principalService.findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);

        final List<User> usersPaginatedAndSorted = Lists.transform(principalsPaginatedAndSorted.getContent(), new PrincipalToUserFunction());

        return new PageImpl<User>(usersPaginatedAndSorted, new PageRequest(page, size, constructSort(sortBy, sortOrder)), principalsPaginatedAndSorted.getTotalElements());
    }

    @Override
    public List<User> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        return findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder).getContent();
    }

    // create

    @Override
    public User create(final User entity) {
        final Principal newPrincipalEntity = new Principal(entity.getName(), entity.getPassword(), entity.getRoles());
        principalService.create(newPrincipalEntity);
        entity.setId(newPrincipalEntity.getId());
        return entity;
    }

    // update

    @Override
    public void update(final User entity) {
        final Principal principalToUpdate = RestPreconditions.checkNotNull(principalService.findOne(entity.getId()));

        principalToUpdate.setName(entity.getName());
        principalToUpdate.setRoles(entity.getRoles());

        principalService.update(principalToUpdate);
    }

    // delete

    @Override
    public void delete(final long id) {
        principalService.delete(id);
    }

    @Override
    public void deleteAll() {
        principalService.deleteAll();
    }

    // count

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    // other

    @Override
    public User getCurrentUser() {
        final Principal principal = principalService.getCurrentPrincipal();
        return new User(principal);
    }

    // util

    final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
