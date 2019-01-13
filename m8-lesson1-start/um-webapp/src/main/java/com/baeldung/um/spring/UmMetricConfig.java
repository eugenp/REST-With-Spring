package com.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.baeldung.common.metric" })
public class UmMetricConfig {

    public UmMetricConfig() {
        super();
    }

    // beans

}
