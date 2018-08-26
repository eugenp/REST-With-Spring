package com.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.baeldung.client.spring.CommonClientConfig;
import com.baeldung.common.spring.CommonWebConfig;

@Configuration
@ComponentScan({ "com.baeldung.test.common" })
@Import({ CommonClientConfig.class, CommonWebConfig.class })
public class CommonTestConfig {

    public CommonTestConfig() {
        super();
    }

}
