package org.rest.sec.spring.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath*:secContextConfig-test.xml")
@ComponentScan({ "org.rest.sec.model" })
public class ContextTestConfig {
    //
}
