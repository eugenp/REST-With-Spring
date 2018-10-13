package com.baeldung.common.persistence.service;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.common.interfaces.IWithName;

public interface IService<T extends IWithName> extends IRawService<T>, IByNameApi<T> {

    //

}
