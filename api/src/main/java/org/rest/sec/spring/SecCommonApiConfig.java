package org.rest.sec.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.rest.sec.common" })
public class SecCommonApiConfig {

    public SecCommonApiConfig() {
        super();
    }

}
