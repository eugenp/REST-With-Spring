package org.rest.sec.persistence.setup;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationContextInitializer.class);

    //

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
	final ConfigurableEnvironment environment = applicationContext.getEnvironment();
	try {
	    final String target = environment.getProperty("target");
	    environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:env-" + target + ".properties"));

	    final String activeProfiles = environment.getProperty("spring.profiles.active");
	    logger.info("The active profile is: " + activeProfiles);

	    environment.setActiveProfiles(activeProfiles.split(","));
	} catch (final IOException e) {
	    // it's OK if the file is not there. we will just log that info.
	    logger.info("didn't find env.properties in classpath so not loading it in the AppContextInitialized");
	}
    }

}
