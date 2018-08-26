package com.baeldung.client.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.baeldung.common.client", "com.baeldung.client" })
public class CommonClientConfig {

    public CommonClientConfig() {
        super();
    }

}
