package org.rest.common.event;

import org.springframework.context.ApplicationEvent;

public final class BeforeCleanupEvent extends ApplicationEvent {

    public BeforeCleanupEvent(final Object sourceToSet) {
        super(sourceToSet);
    }

    // API

}
