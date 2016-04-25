package org.baeldung.um.persistence.setup;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.google.common.base.Preconditions;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private final Logger logger = LoggerFactory.getLogger(MyApplicationContextInitializer.class);

    private static final String ENV_TARGET = "envTarget";

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
        String envTarget = null;
        try {
            envTarget = getEnvTarget(environment);
            environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:env-" + envTarget + ".properties"));

            final String activeProfiles = environment.getProperty("spring.profiles.active");
            environment.setActiveProfiles(activeProfiles.split(","));
        } catch (final IOException ioEx) {
            if (envTarget != null) {
                logger.warn("Didn't find env-" + envTarget + ".properties in classpath so not loading it in the AppContextInitialized", ioEx);
            }
        }
    }

    /**
     * @param environment
     * @return The env target variable.
     */
    private String getEnvTarget(final ConfigurableEnvironment environment) {
        String target = environment.getProperty(ENV_TARGET);
        if (target == null) {
            logger.warn("Didn't find a value for {} in the current Environment!", ENV_TARGET);
        }

        if (target == null) {
            logger.info("Didn't find a value for {} in the current Environment!, using the default `dev`", ENV_TARGET);
            target = "dev";
        }

        return Preconditions.checkNotNull(target);
    }

}
