package org.rest.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * This event should be sent before performing cleanup on shutdown <br>
 */
public final class BeforeCleanupEvent extends ApplicationEvent {

    public BeforeCleanupEvent(final Object sourceToSet) {
        super(sourceToSet);
    }

    // API

}
