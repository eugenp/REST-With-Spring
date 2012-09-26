package org.rest.common.client.template;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.IOperations;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.INameSupport;

public interface IClientTemplate<T extends INameableEntity> extends IOperations<T>, INameSupport<T>, ITemplateWithUri<T> {

    IClientTemplate<T> givenAuthenticated(final String username, final String password);

    //

    String getURI();

    // create

    T create(final T resource, final Pair<String, String> credentials);

    T findOne(final long id, final Pair<String, String> credentials);

}
