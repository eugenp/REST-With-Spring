package com.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@ComponentScan({ "com.baeldung.um.web" })
@EnableWebFlux
public class UmWebConfig {

    public UmWebConfig() {
        super();
    }

    // configuration
}
