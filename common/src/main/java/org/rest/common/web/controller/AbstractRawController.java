package org.rest.common.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.http.HttpHeaders;
import org.rest.common.event.MultipleResourcesRetrievedEvent;
import org.rest.common.event.PaginatedResultsRetrievedEvent;
import org.rest.common.event.ResourceCreatedEvent;
import org.rest.common.event.SingleResourceRetrievedEvent;
import org.rest.common.exceptions.BadRequestException;
import org.rest.common.exceptions.ConflictException;
import org.rest.common.exceptions.ForbiddenException;
import org.rest.common.exceptions.ResourceNotFoundException;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.persistence.service.IRawService;
import org.rest.common.util.QueryConstants;
import org.rest.common.web.RestPreconditions;
import org.rest.common.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class AbstractRawController<T extends IEntity> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Class<T> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractRawController(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // search

    public List<T> searchInternal(@RequestParam(QueryConstants.Q_PARAM) final String queryString) {
        try {
            return getService().searchAll(queryString);
        } catch (final IllegalStateException illEx) {
            logger.error("IllegalStateException on search operation");
            logger.warn("IllegalStateException on search operation", illEx);
            throw new BadRequestException(illEx);
        }
    }

    public List<T> searchInternalPaginated(@RequestParam(QueryConstants.Q_PARAM) final String queryString, final int page, final int size) {
        try {
            return getService().searchPaginated(queryString, page, size);
        } catch (final IllegalStateException illEx) {
            logger.error("IllegalStateException on search operation");
            logger.warn("IllegalStateException on search operation", illEx);
            throw new BadRequestException(illEx);
        }
    }

    // find - one

    protected final T findOneInternal(final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final T resource = findOneInternal(id);

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent<T>(clazz, uriBuilder, response));

        return resource;
    }

    protected final T findOneInternal(final Long id) {
        T resource = null;
        try {
            resource = RestPreconditions.checkNotNull(getService().findOne(id));
        } catch (final InvalidDataAccessApiUsageException ex) {
            logger.error("InvalidDataAccessApiUsageException on find operation");
            logger.warn("InvalidDataAccessApiUsageException on find operation", ex);
            throw new ConflictException(ex);
        } catch (final NullPointerException npe) { // note: for now, a NPE from the service layer is related to the getCurrent... type operations - this may change
            logger.error("NullPointerException on find operation");
            logger.warn("NullPointerException on find operation", npe);
            throw new ForbiddenException(npe);
        }
        return resource;
    }

    // find - all

    protected final List<T> findAllInternal(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        if (request.getParameterNames().hasMoreElements()) {
            throw new ResourceNotFoundException();
        }

        eventPublisher.publishEvent(new MultipleResourcesRetrievedEvent<T>(clazz, uriBuilder, response));
        return getService().findAll();
    }

    protected final void findAllRedirectToPagination(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String locationValue = uriBuilder.path(WebConstants.PATH_SEP + resourceName).build().encode().toUriString() + QueryConstants.QUESTIONMARK + "page=0&size=10";

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

    protected final List<T> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        Page<T> resultPage = null;
        try {
            resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        } catch (final InvalidDataAccessApiUsageException apiEx) {
            logger.error("InvalidDataAccessApiUsageException on find all operation");
            logger.warn("InvalidDataAccessApiUsageException on find all operation", apiEx);
            throw new BadRequestException(apiEx);
        } catch (final IllegalArgumentException apiEx) { // thrown by PageRequest in case the page parameters are wrong
            logger.error("IllegalArgumentException on find all operation");
            logger.warn("IllegalArgumentException on find all operation", apiEx);
            throw new BadRequestException(apiEx);
        }

        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<T>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<T> findPaginatedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        Page<T> resultPage = null;
        try {
            resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        } catch (final InvalidDataAccessApiUsageException apiEx) {
            logger.error("InvalidDataAccessApiUsageException on find all operation");
            logger.warn("InvalidDataAccessApiUsageException on find all operation", apiEx);
            throw new BadRequestException(apiEx);
        }

        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<T>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<T> findAllSortedInternal(final String sortBy, final String sortOrder) {
        List<T> resultPage = null;
        try {
            resultPage = getService().findAllSorted(sortBy, sortOrder);
        } catch (final InvalidDataAccessApiUsageException apiEx) {
            logger.error("InvalidDataAccessApiUsageException on find all operation");
            logger.warn("InvalidDataAccessApiUsageException on find all operation", apiEx);
            throw new BadRequestException(apiEx);
        } catch (final PropertyReferenceException springDataEx) {
            logger.info("PropertyReferenceException on find all operation");
            logger.debug("PropertyReferenceException on find all operation", springDataEx);
            throw new BadRequestException(springDataEx);
        }

        return resultPage;
    }

    // save/create/persist

    protected final void createInternal(final T resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        try {
            getService().create(resource);
        }
        // this is so that the service layer can MANUALLY throw exceptions that get handled by the exception translation mechanism
        catch (final IllegalStateException illegalState) {
            logger.error("IllegalArgumentException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("IllegalArgumentException on create operation for: {}", resource.getClass().getSimpleName(), illegalState);
            throw new ConflictException(illegalState);
        } catch (final DataIntegrityViolationException ex) { // on unique constraint
            logger.error("DataIntegrityViolationException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("DataIntegrityViolationException on create operation for: {}", resource.getClass().getSimpleName(), ex);
            throw new ConflictException(ex);
        } catch (final InvalidDataAccessApiUsageException dataEx) { // on saving a new Resource that also contains new/unsaved entities
            logger.error("InvalidDataAccessApiUsageException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("InvalidDataAccessApiUsageException on create operation for: {}", resource.getClass().getSimpleName(), dataEx);
            throw new ConflictException(dataEx);
        } catch (final DataAccessException dataEx) {
            logger.error("DataAccessException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("ataAccessException on create operation for: {}", resource.getClass().getSimpleName(), dataEx);
            throw new ConflictException(dataEx);
        } catch (final ConstraintViolationException validEx) { // bean validation
            logger.error("ConstraintViolationException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("ConstraintViolationException on create operation for: {}", resource.getClass().getSimpleName(), validEx);
            throw new BadRequestException(validEx);
        }

        // - note: mind the autoboxing and potential NPE when the resource has null id at this point (likely when working with DTOs)
        eventPublisher.publishEvent(new ResourceCreatedEvent<T>(clazz, uriBuilder, response, resource.getId().toString()));
    }

    // update

    /**
     * - note: the operation is IDEMPOTENT <br/>
     */
    protected final void updateInternal(final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

        try {
            getService().update(resource);
        } catch (final IllegalStateException illegalState) {
            // this is so that the service layer can MANUALLY throw exceptions that get handled by the exception translation mechanism
            logger.error("IllegalArgumentException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("IllegalArgumentException on create operation for: {}", resource.getClass().getSimpleName(), illegalState);
            throw new ConflictException(illegalState);
        } catch (final InvalidDataAccessApiUsageException dataEx) {
            logger.error("InvalidDataAccessApiUsageException on update operation for: {}", resource.getClass().getSimpleName());
            logger.warn("InvalidDataAccessApiUsageException on update operation for: {}", resource.getClass().getSimpleName(), dataEx);
            throw new ConflictException(dataEx);
        } catch (final DataIntegrityViolationException dataEx) { // on unique constraint
            logger.error("DataIntegrityViolationException on update operation for: {}", resource.getClass().getSimpleName());
            logger.warn("DataIntegrityViolationException on update operation for: {}", resource.getClass().getSimpleName(), dataEx);
            throw new ConflictException(dataEx);
        } catch (final ConstraintViolationException validEx) { // bean validation
            logger.error("ConstraintViolationException on create operation for: {}", resource.getClass().getSimpleName());
            logger.warn("ConstraintViolationException on create operation for: {}", resource.getClass().getSimpleName(), validEx);
            throw new BadRequestException(validEx);
        }

    }

    // delete/remove

    protected final void deleteByIdInternal(final long id) {
        try {
            getService().delete(id);
        } catch (final InvalidDataAccessApiUsageException dataEx) {
            logger.error("InvalidDataAccessApiUsageException on delete operation");
            logger.warn("InvalidDataAccessApiUsageException on delete operation", dataEx);
            throw new ResourceNotFoundException(dataEx);
        } catch (final DataAccessException dataEx) {
            logger.error("DataAccessException on delete operation");
            logger.warn("DataAccessException on delete operation", dataEx);
        } catch (final IllegalStateException stateEx) {
            logger.error("IllegalStateException on delete operation");
            logger.warn("IllegalStateException on delete operation", stateEx);
            throw new ResourceNotFoundException(stateEx);
        }
    }

    // count

    protected final long countInternal() {
        try {
            return getService().count();
        } catch (final InvalidDataAccessApiUsageException dataEx) {
            logger.error("InvalidDataAccessApiUsageException on count operation");
            logger.warn("InvalidDataAccessApiUsageException on count operation", dataEx);
            throw new ResourceNotFoundException(dataEx);
        } catch (final DataAccessException dataEx) {
            logger.error("DataAccessException on count operation");
            logger.warn("DataAccessException on count operation", dataEx);
            throw new ConflictException(dataEx);
        }
    }

    // template method

    protected abstract IRawService<T> getService();

}
