package org.baeldung.common.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.common.interfaces.IOperations;
import org.baeldung.common.persistence.model.IEntity;
import org.baeldung.common.search.ClientOperation;
import org.springframework.data.domain.Page;

public interface IRawService<T extends IEntity> extends IOperations<T> {

    // search

    List<T> searchAll(final String queryString);

    List<T> searchPaginated(final String queryString, final int page, final int size);

    Page<T> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints);

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);

}
