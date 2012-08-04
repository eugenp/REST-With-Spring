package org.rest.common.client.template;

import java.util.List;

import org.rest.common.IOperations;
import org.rest.common.persistence.model.IEntity;

public interface IClientTemplate<T extends IEntity> extends IOperations<T> {

    IClientTemplate<T> givenAuthenticated();

    T findOneByAttributes(final String... attributes);

    List<T> findAllByAttributes(final String... attributes);

    T findOneByName(final String name);

    T findOneByURI(final String uri);

    String createAsURI(final T resource);

}
