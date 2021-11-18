package com.baeldung.common.persistence.service;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, IByNameApi<T> {

    //

}
