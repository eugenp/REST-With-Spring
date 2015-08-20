package org.baeldung.rest.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.baeldung.rest.common.client", "org.baeldung.rest.client" })
public class ClientTestConfig {

    // API

}
