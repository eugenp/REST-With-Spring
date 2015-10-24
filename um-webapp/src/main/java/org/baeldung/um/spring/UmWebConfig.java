package org.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({ "org.baeldung.common.web", "org.baeldung.um.web" })
@EnableWebMvc
public class UmWebConfig extends WebMvcConfigurerAdapter {

    public UmWebConfig() {
        super();
    }

    // beans

}
