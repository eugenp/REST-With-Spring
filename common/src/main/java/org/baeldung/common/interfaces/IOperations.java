package org.baeldung.common.interfaces;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.common.search.ClientOperation;

public interface IOperations<T extends Serializable> {

    // find - one

    T findOne(final long id);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAll();

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllSorted(final String sortBy, final String sortOrder);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginated(final int page, final int size);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

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

    List<T> searchAll(final Triple<String, ClientOperation, String>... constraints);

    T searchOne(final Triple<String, ClientOperation, String>... constraints);

}
