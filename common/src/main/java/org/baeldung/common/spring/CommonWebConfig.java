package org.baeldung.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.baeldung.common.web" })
public class CommonWebConfig {

    public CommonWebConfig() {
        super();
    }

}
