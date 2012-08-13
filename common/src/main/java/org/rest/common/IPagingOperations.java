package org.rest.common;

import java.util.List;

import org.rest.common.persistence.model.IEntity;

public interface IPagingOperations<T extends IEntity> {

    // find - all

    List<T> findAllPaginated(final int page, final int size);

}
