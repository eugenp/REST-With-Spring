package com.baeldung.um.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

import com.baeldung.um.persistence.setup.MyApplicationContextInitializer;
import com.baeldung.um.spring.ResourceServerConfiguration;
import com.baeldung.um.spring.UmContextConfig;
import com.baeldung.um.spring.UmPersistenceJpaConfig;
import com.baeldung.um.spring.UmServiceConfig;
import com.baeldung.um.spring.UmWebConfig;

@SpringBootApplication(exclude = { // @formatter:off
        ErrorMvcAutoConfiguration.class
})// @formatter:on
public class UmApp {

    private final static Object[] CONFIGS = { // @formatter:off
            UmContextConfig.class,
            UmPersistenceJpaConfig.class,
            UmServiceConfig.class,
            UmWebConfig.class,            

            UmApp.class,

            ResourceServerConfiguration.class
    }; // // @formatter:on

    //    

    public static void main(final String... args) {
        final SpringApplication springApplication = new SpringApplication(CONFIGS);
        springApplication.addInitializers(new MyApplicationContextInitializer());
        springApplication.run(args);
    }

}
