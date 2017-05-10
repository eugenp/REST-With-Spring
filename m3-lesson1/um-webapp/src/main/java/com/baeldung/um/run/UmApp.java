package com.baeldung.um.run;

import com.baeldung.um.persistence.setup.MyApplicationContextInitializer;
import com.baeldung.um.spring.UmContextConfig;
import com.baeldung.um.spring.UmJavaSecurityConfig;
import com.baeldung.um.spring.UmPersistenceJpaConfig;
import com.baeldung.um.spring.UmServiceConfig;
import com.baeldung.um.spring.UmServletConfig;
import com.baeldung.um.spring.UmWebConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = { // @formatter:off
        SecurityAutoConfiguration.class
        , ErrorMvcAutoConfiguration.class
}) // @formatter:on
@Import({ // @formatter:off
    UmContextConfig.class,
    UmPersistenceJpaConfig.class,
    UmServiceConfig.class,
    UmWebConfig.class,
    UmServletConfig.class,
    UmJavaSecurityConfig.class
}) // @formatter:on
public class UmApp extends SpringBootServletInitializer {

    public UmApp() {
        super();
    }

    //

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.initializers(new MyApplicationContextInitializer()).sources(UmApp.class);
    }

    public static void main(final String... args) {
        new SpringApplicationBuilder(UmApp.class).initializers(new MyApplicationContextInitializer()).run(args);
    }

}
