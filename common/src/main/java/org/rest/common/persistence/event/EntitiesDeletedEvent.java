package org.rest.common.persistence.event;

import org.rest.common.persistence.model.IEntity;
import org.springframework.context.ApplicationEvent;

import com.google.common.base.Preconditions;

/**
 * This event is fired after all entities are deleted.
 */
public final class EntitiesDeletedEvent<T extends IEntity> extends ApplicationEvent {

    private final Class<T> clazz;

    public EntitiesDeletedEvent(final Object sourceToSet, final Class<T> clazzToSet) {
        super(sourceToSet);

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // API

    public final Class<T> getClazz() {
        return clazz;
    }

}
