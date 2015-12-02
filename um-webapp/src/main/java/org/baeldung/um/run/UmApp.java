package org.baeldung.um.run;

import org.baeldung.um.spring.UmContextConfig;
import org.baeldung.um.spring.UmJavaSecurityConfig;
import org.baeldung.um.spring.UmPersistenceJpaConfig;
import org.baeldung.um.spring.UmServiceConfig;
import org.baeldung.um.spring.UmServletConfig;
import org.baeldung.um.spring.UmWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication(exclude = { // @formatter:off
        SecurityAutoConfiguration.class
        , ErrorMvcAutoConfiguration.class
})// @formatter:on
public class UmApp extends SpringBootServletInitializer {

    private final static Object[] CONFIGS = { UmContextConfig.class, UmPersistenceJpaConfig.class, UmServiceConfig.class, UmWebConfig.class, UmServletConfig.class, UmJavaSecurityConfig.class };

    //

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(CONFIGS);
    }

    public static void main(final String... args) {
        SpringApplication.run(CONFIGS, args);
    }

}
