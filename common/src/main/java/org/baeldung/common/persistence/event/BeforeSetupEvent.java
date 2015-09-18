package org.baeldung.common.persistence.event;

import org.springframework.context.ApplicationEvent;

public final class BeforeSetupEvent extends ApplicationEvent {

    public BeforeSetupEvent(final Object sourceToSet) {
        super(sourceToSet);
    }

    // API

}
