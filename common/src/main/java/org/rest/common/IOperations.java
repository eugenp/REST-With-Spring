package org.rest.common;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;

public interface IOperations<T extends IEntity> extends IPagingOperations<T> {

    // get

    T findOne(final long id);

    List<T> findAll();

    List<T> findAllSorted(final String sortBy, final String sortOrder);

    // create

    T create(final T resource);

    // update

    void update(final T resource);

    // delete

    void delete(final long id);

    void deleteAll();

    // count

    long count();

    // search

    List<T> search(final Triple<String, ClientOperation, String>... constraints);

}
