package org.rest.common.client.template;

import org.rest.common.IEntity;

public interface IEntityOperations<T extends IEntity> {

    T createNewEntity();

    void invalidate(final T entity);

    void change(final T resource);

}
