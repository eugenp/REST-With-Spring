package com.baeldung.um.spring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.baeldung.common.metric" })
public class UmMetricConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public UmMetricConfig() {
        super();
    }

    // beans
    @Bean(name = "privilegeCounter")
    public Counter privilegeCounter(MeterRegistry registry) {
        logger.info("Register the counter for find by name [privilege]");
        return registry.counter("service.privilege.findByName");
    }

    @Bean(name = "userCounter")
    public Counter userCounter(MeterRegistry registry) {
        logger.info("Register the counter for find by name [user]");
        return registry.counter("service.user.findByName");
    }

}
