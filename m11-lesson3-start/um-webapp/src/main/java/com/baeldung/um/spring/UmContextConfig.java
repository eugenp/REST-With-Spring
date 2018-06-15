package com.baeldung.um.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UmContextConfig {

    public UmContextConfig() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
