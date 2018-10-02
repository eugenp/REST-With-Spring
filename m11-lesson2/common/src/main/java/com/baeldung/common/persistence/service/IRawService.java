package com.baeldung.common.persistence.service;

import org.springframework.data.domain.Page;

import com.baeldung.common.interfaces.IOperations;
import com.baeldung.common.interfaces.IWithName;

public interface IRawService<T extends IWithName> extends IOperations<T> {

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);

    Page<T> findAllPaginatedRaw(final int page, final int size);

}
