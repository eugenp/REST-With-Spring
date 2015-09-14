package org.baeldung.client.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.baeldung.common.client", "org.baeldung.client" })
public class CommonClientConfig {

    public CommonClientConfig() {
        super();
    }

}
