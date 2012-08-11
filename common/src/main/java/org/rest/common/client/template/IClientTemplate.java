package org.rest.common.client.template;

import java.util.List;

import org.rest.common.IOperations;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.INameSupport;

public interface IClientTemplate<T extends INameableEntity> extends IOperations<T>, INameSupport<T>, ITemplateAsURI<T> {

    IClientTemplate<T> givenAuthenticated();

    T findOneByAttributes(final String... attributes);

    List<T> findAllByAttributes(final String... attributes);

    T findOneByURI(final String uri);

}
