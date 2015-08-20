package org.baeldung.rest.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.baeldung.rest.common.client" })
public class CommonClientConfig {

    public CommonClientConfig() {
        super();
    }

}
