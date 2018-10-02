package com.baeldung.um.persistence.setup;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public MyApplicationContextInitializer() {
        super();
    }

    //

    /**
     * Sets the active profile.
     */
    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        final String activeProfiles = environment.getProperty("spring.profiles.active");

        if (activeProfiles != null) {
            environment.setActiveProfiles(activeProfiles.split(","));
        }
    }

}
