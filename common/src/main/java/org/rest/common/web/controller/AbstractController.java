package org.rest.common.web.controller;

import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.IService;

public abstract class AbstractController<T extends INameableEntity> extends AbstractRawController<T> {

    public AbstractController(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // template method

    @Override
    protected abstract IService<T> getService();

}
