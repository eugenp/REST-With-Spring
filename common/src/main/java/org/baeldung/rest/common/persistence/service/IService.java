package org.baeldung.rest.common.persistence.service;

import org.baeldung.rest.common.client.IByNameApi;
import org.baeldung.rest.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, IByNameApi<T> {

    //

}
