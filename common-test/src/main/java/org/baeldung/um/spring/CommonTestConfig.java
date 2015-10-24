package org.baeldung.um.spring;

import org.baeldung.client.spring.CommonClientConfig;
import org.baeldung.common.spring.CommonWebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "org.baeldung.test.common" })
@Import({ CommonClientConfig.class, CommonWebConfig.class })
public class CommonTestConfig {

    public CommonTestConfig() {
        super();
    }

}
