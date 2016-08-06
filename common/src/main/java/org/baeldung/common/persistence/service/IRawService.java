package org.baeldung.common.persistence.service;

import org.baeldung.common.interfaces.IOperations;
import org.baeldung.common.persistence.model.IEntity;
import org.springframework.data.domain.Page;

public interface IRawService<T extends IEntity> extends IOperations<T> {

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);

}
