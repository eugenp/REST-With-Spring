package org.rest.spring.testing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.rest.testing", "org.rest.sec.testing" })
public class TestingConfig {
    //
}
