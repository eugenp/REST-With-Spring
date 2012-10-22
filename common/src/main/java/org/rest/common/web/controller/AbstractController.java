package org.rest.common.web.controller;

import org.rest.common.exceptions.ConflictException;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.persistence.service.IService;
import org.rest.common.web.RestPreconditions;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public abstract class AbstractController<T extends INameableEntity> extends AbstractRawController<T> {

    public AbstractController(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // find - one

    protected final T findByNameInternal(final String name) {
        T resource = null;
        try {
            resource = RestPreconditions.checkNotNull(getService().findByName(name));
        } catch (final InvalidDataAccessApiUsageException ex) {
            logger.error("InvalidDataAccessApiUsageException on find operation");
            logger.warn("InvalidDataAccessApiUsageException on find operation", ex);
            throw new ConflictException(ex);
        }

        return resource;
    }

    // template method

    @Override
    protected abstract IService<T> getService();

}
