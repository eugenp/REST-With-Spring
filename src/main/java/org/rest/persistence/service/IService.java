package org.rest.persistence.service;

import org.rest.common.IEntity;
import org.rest.common.IRestDao;
import org.springframework.data.domain.Page;

public interface IService<T extends IEntity> extends IRestDao<T> {

    // find - all

    Page<T> findPaginated(final int page, final int size, final String sortBy);

    // delete

    void deleteAll();

}
