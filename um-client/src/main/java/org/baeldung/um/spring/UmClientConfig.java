package org.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.baeldung.um.client" })
public class UmClientConfig {

    public UmClientConfig() {
        super();
    }

}
