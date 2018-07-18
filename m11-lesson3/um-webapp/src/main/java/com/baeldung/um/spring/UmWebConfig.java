package com.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@ComponentScan({ "com.baeldung.um.web" })
@EnableWebFlux
public class UmWebConfig implements WebFluxConfigurer {

    public UmWebConfig() {
        super();
    }

    // configuration
}
