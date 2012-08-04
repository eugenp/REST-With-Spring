package org.rest.common.client;

import org.rest.common.persistence.model.IEntity;

public interface IEntityOperations<T extends IEntity> {

    T createNewEntity();

    void invalidate(final T entity);

    void change(final T resource);

}
