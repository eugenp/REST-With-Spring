package org.rest.common.persistence.service;

import org.rest.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, INameSupport<T> {

    //

}
