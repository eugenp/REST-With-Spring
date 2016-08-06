package org.baeldung.common.persistence.service;

import org.baeldung.common.interfaces.IByNameApi;
import org.baeldung.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, IByNameApi<T> {

    //

}
