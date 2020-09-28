package com.baeldung.common.persistence.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.common.persistence.exception.MyEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.common.persistence.event.AfterEntitiesDeletedEvent;
import com.baeldung.common.persistence.event.AfterEntityCreateEvent;
import com.baeldung.common.persistence.event.AfterEntityDeleteEvent;
import com.baeldung.common.persistence.event.AfterEntityUpdateEvent;
import com.baeldung.common.persistence.event.BeforeEntityCreateEvent;
import com.baeldung.common.persistence.event.BeforeEntityDeleteEvent;
import com.baeldung.common.persistence.event.BeforeEntityUpdateEvent;
import com.baeldung.common.persistence.model.IEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractRawService<T extends IEntity> implements IRawService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Class<T> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractRawService(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

    // API

    // find - one

    @Override
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        Optional<T> entity = getDao().findById(id);
        return entity.orElse(null);
    }

    // find - all

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return getDao().findAll(PageRequest.of(page, size, sortInfo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        final List<T> content = getDao().findAll(PageRequest.of(page, size, sortInfo))
            .getContent();
        if (content == null) {
            return Lists.newArrayList();
        }
        return content;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedRaw(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginated(final int page, final int size) {
        final List<T> content = getDao().findAll(PageRequest.of(page, size))
            .getContent();
        if (content == null) {
            return Lists.newArrayList();
        }
        return content;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return Lists.newArrayList(getDao().findAll(sortInfo));
    }

    // save/create/persist

    @Override
    public T create(final T entity) {
        Preconditions.checkNotNull(entity);

        eventPublisher.publishEvent(new BeforeEntityCreateEvent<T>(this, clazz, entity));
        final T persistedEntity = getDao().save(entity);
        eventPublisher.publishEvent(new AfterEntityCreateEvent<T>(this, clazz, persistedEntity));

        return persistedEntity;
    }

    // update/merge

    @Override
    public void update(final T entity) {
        Preconditions.checkNotNull(entity);

        eventPublisher.publishEvent(new BeforeEntityUpdateEvent<T>(this, clazz, entity));
        getDao().save(entity);
        eventPublisher.publishEvent(new AfterEntityUpdateEvent<T>(this, clazz, entity));
    }

    // delete

    @Override
    public void deleteAll() {
        getDao().deleteAll();
        eventPublisher.publishEvent(new AfterEntitiesDeletedEvent<T>(this, clazz));
    }

    @Override
    public void delete(final long id) {
        final Optional<T> entity = getDao().findById(id);
        if (entity.isPresent()) {
            eventPublisher.publishEvent(new BeforeEntityDeleteEvent<T>(this, clazz, entity.get()));
            getDao().delete(entity.get());
            eventPublisher.publishEvent(new AfterEntityDeleteEvent<T>(this, clazz, entity.get()));
        } else {
            throw new MyEntityNotFoundException();
        }
    }


    // count

    @Override
    public long count() {
        return getDao().count();
    }

    // template method

    protected abstract PagingAndSortingRepository<T, Long> getDao();

    protected abstract JpaSpecificationExecutor<T> getSpecificationExecutor();

    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = Sort.unsorted();
        if (sortBy != null) {
            sortInfo = Sort.by(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
