package org.rest.sec.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.rest.common.client", "org.rest.sec.client" })
public class ClientTestConfig {

    // API

}
