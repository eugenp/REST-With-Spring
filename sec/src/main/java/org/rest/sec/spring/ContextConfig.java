package org.rest.sec.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ImportResource("classpath*:secContextConfig.xml")
@ComponentScan({ "org.rest.sec.model" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource({ "classpath:env-${envTarget:dev}.properties", "classpath:web-${webTarget:dev}.properties" })
public class ContextConfig {

    public ContextConfig() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        return pspc;
    }

}
