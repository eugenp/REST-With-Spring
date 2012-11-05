package org.rest.common.client.template;

import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.INameSupport;

public interface IClientTemplate<T extends INameableEntity> extends IRawClientTemplate<T>, INameSupport<T> {

    //

}
