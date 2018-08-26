package com.baeldung.common.persistence.event;

import org.springframework.context.ApplicationEvent;

import com.baeldung.common.interfaces.IWithName;
import com.google.common.base.Preconditions;

/**
 * This event is fired after all entities are deleted.
 */
public final class AfterEntitiesDeletedEvent<T extends IWithName> extends ApplicationEvent {

    private final Class<T> clazz;

    public AfterEntitiesDeletedEvent(final Object sourceToSet, final Class<T> clazzToSet) {
        super(sourceToSet);

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // API

    public final Class<T> getClazz() {
        return clazz;
    }

}
