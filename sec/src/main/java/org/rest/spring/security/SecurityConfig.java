package org.rest.spring.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("org.rest.security")
@ImportResource({ "classpath*:secSecurityConfig.xml" })
public class SecurityConfig {

    public SecurityConfig() {
        super();
    }

}
