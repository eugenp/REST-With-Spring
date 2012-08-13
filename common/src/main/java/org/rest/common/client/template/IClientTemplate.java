package org.rest.common.client.template;

import org.rest.common.IOperations;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.INameSupport;

public interface IClientTemplate<T extends INameableEntity> extends IOperations<T>, INameSupport<T>, ITemplateAsURI<T> {

    IClientTemplate<T> givenAuthenticated();

    //

    String getURI();

}
