package org.baeldung.common.persistence.service;

import org.baeldung.common.persistence.model.INameableEntity;

public abstract class AbstractService<T extends INameableEntity> extends AbstractRawService<T>implements IService<T> {

    public AbstractService(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // API

    // find - one

}
