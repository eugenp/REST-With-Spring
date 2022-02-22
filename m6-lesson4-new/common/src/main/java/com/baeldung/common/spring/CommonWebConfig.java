package com.baeldung.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.baeldung.common.web" })
public class CommonWebConfig {

    public CommonWebConfig() {
        super();
    }

}
