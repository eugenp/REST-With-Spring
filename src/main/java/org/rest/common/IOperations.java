package org.rest.common;

import java.util.List;

public interface IOperations<T extends IEntity> {

    // get

    T findOne(final long id);

    List<T> findAll();

    // create

    T create(final T resource);

    // update

    void update(final T resource);

    // delete

    void delete(final long id);

    void deleteAll();

}
