package org.rest.common.persistence.service;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.IOperations;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;
import org.springframework.data.domain.Page;

public interface IService<T extends IEntity> extends IOperations<T> {

    // search

    Page<T> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints);

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);

}
