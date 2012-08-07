package org.rest.sec.spring.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath*:secContextConfig.xml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ContextConfig {

    public ContextConfig() {
        super();
    }

}
