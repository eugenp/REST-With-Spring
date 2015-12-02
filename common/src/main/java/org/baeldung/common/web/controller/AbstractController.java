package org.baeldung.common.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.persistence.model.IEntity;
import org.baeldung.common.web.RestPreconditions;
import org.baeldung.common.web.events.AfterResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class AbstractController<D extends IDto, E extends IEntity> extends AbstractReadOnlyController<D, E> {

    @Autowired
    public AbstractController(final Class<D> clazzToSet) {
        super(clazzToSet);
    }

    // save/create/persist

    protected final void createInternal(final E resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        final E existingResource = getService().create(resource);

        // - note: mind the autoboxing and potential NPE when the resource has null id at this point (likely when working with DTOs)
        eventPublisher.publishEvent(new AfterResourceCreatedEvent<D>(clazz, uriBuilder, response, existingResource.getId().toString()));
    }

    // update

    /**
     * - note: the operation is IDEMPOTENT <br/>
     */
    protected final void updateInternal(final long id, final E resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkRequestState(resource.getId() == id);
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

        getService().update(resource);
    }

    // delete/remove

    protected final void deleteByIdInternal(final long id) {
        // InvalidDataAccessApiUsageException - ResourceNotFoundException
        // IllegalStateException - ResourceNotFoundException
        // DataAccessException - ignored
        getService().delete(id);
    }

}
