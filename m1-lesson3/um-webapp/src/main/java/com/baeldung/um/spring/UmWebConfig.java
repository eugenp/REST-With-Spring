package com.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({ "com.baeldung.um.web" })
@EnableWebMvc
public class UmWebConfig {

    public UmWebConfig() {
        super();
    }
}
