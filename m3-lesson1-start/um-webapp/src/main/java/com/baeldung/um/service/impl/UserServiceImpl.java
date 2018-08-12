package com.baeldung.um.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.common.persistence.ServicePreconditions;
import com.baeldung.um.persistence.model.Principal;
import com.baeldung.um.service.IPrincipalService;
import com.baeldung.um.service.IUserService;
import com.baeldung.um.web.dto.UserDto;
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

    // find - one

    @Override
    @Transactional(readOnly = true)
    public UserDto findByName(final String name) {
        final Principal principal = principalService.findByName(name);
        return new UserDto(principal);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findOne(final long id) {
        final Principal principal = principalService.findOne(id);
        if (principal == null) {
            return null;
        }
        return new UserDto(principal);
    }

    // find - many

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        final List<Principal> principals = principalService.findAll();
        final List<UserDto> userDtos = principals.stream().map(this::convert).collect(Collectors.toList());
        return Lists.newArrayList(userDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllSorted(final String sortBy, final String sortOrder) {
        final List<Principal> principals = principalService.findAllSorted(sortBy, sortOrder);
        final List<UserDto> userDtos = principals.stream().map(this::convert).collect(Collectors.toList());
        return Lists.newArrayList(userDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllPaginated(final int page, final int size) {
        final List<Principal> principals = principalService.findAllPaginated(page, size);
        final List<UserDto> userDtos = principals.stream().map(this::convert).collect(Collectors.toList());
        return Lists.newArrayList(userDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<Principal> principals = principalService.findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        final List<UserDto> userDtos = principals.getContent().stream().map(this::convert).collect(Collectors.toList());
        return new PageImpl<UserDto>(userDtos, new PageRequest(page, size, constructSort(sortBy, sortOrder)), principals.getTotalElements());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> findAllPaginatedRaw(final int page, final int size) {
        final Page<Principal> principals = principalService.findAllPaginatedRaw(page, size);
        final List<UserDto> userDtos = principals.getContent().stream().map(this::convert).collect(Collectors.toList());
        return new PageImpl<UserDto>(userDtos, new PageRequest(page, size), principals.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        return findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder).getContent();
    }  

    // create

    @Override
    public UserDto create(final UserDto dto) {
        final Principal newPrincipalEntity = new Principal(dto);
        principalService.create(newPrincipalEntity);
        dto.setId(newPrincipalEntity.getId());
        return dto;
    }

    // update

    @Override
    public void update(final UserDto dto) {
        final Principal principalToUpdate = ServicePreconditions.checkEntityExists(principalService.findOne(dto.getId()));

        principalToUpdate.setName(dto.getName());
        principalToUpdate.setEmail(dto.getEmail());
        principalToUpdate.setRoles(dto.getRoles());

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
        return principalService.count();
    }

    // other

    @Override
    public UserDto getCurrentUser() {
        final Principal principal = principalService.getCurrentPrincipal();
        return new UserDto(principal);
    }

    // UTIL

    private final UserDto convert(final Principal principal) {
        return new UserDto(principal);
    }

    private final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
