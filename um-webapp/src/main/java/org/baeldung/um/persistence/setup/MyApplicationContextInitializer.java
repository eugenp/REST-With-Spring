package org.baeldung.um.persistence.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        logger.info("The active profiles are: {}", activeProfiles);

        environment.setActiveProfiles(activeProfiles.split(","));
    }

}
