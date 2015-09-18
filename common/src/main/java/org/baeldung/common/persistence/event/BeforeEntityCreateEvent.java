package org.baeldung.common.persistence.event;

import org.baeldung.common.persistence.model.IEntity;
import org.springframework.context.ApplicationEvent;

import com.google.common.base.Preconditions;

public final class BeforeEntityCreateEvent<T extends IEntity> extends ApplicationEvent {
    private final Class<T> clazz;
    private final T entity;

    public BeforeEntityCreateEvent(final Object sourceToSet, final Class<T> clazzToSet, final T entityToSet) {
        super(sourceToSet);

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;

        Preconditions.checkNotNull(entityToSet);
        entity = entityToSet;
    }

    // API

    public final Class<T> getClazz() {
        return clazz;
    }

    public final T getEntity() {
        return Preconditions.checkNotNull(entity);
    }

}
