package org.baeldung.rest.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.baeldung.rest.service" })
public class ServiceConfig {

    public ServiceConfig() {
        super();
    }

    // beans

}
