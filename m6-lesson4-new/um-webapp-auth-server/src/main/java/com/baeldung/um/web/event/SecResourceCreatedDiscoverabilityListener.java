package com.baeldung.um.web.event;

import org.springframework.stereotype.Component;

import com.baeldung.common.web.listeners.ResourceCreatedDiscoverabilityListener;
import com.baeldung.um.util.UmMappings;

@Component
class SecResourceCreatedDiscoverabilityListener extends ResourceCreatedDiscoverabilityListener {

    public SecResourceCreatedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    protected final String getBase() {
        return UmMappings.BASE;
    }

}