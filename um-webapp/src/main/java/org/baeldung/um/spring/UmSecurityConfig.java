package org.baeldung.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("org.baeldung.um.security")
@ImportResource({ "classpath*:umSecurityConfig.xml" })
public class UmSecurityConfig {

    public UmSecurityConfig() {
        super();
    }

}
