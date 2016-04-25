package org.baeldung.um.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class StartupLoggingComponent implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String ENV_TARGET_KEY = "envTarget";
    private static final String PERSISTENCE_TARGET_KEY = "persistenceTarget";
    private static final String ACTIVE_SPRING_PROFILE_KEY = "spring.profiles.active";
    private static final String PERSISTENCE_HOST_KEY = "jdbc.url";

    @Autowired
    private Environment env;

    public StartupLoggingComponent() {
        super();
    }

    //

    @Override
    public void afterPropertiesSet() {
        logger.info("============================================================================");
        try {
            logEnvTarget(env);
            logPersistenceTarget(env);

            logActiveSpringProfile(env);
            logPersistenceData(env);
        } catch (final Exception ex) {
            logger.warn("There was a problem logging data on startup", ex);
        }

        logger.info("============================================================================");
    }

    // UTIL

    private void logEnvTarget(final Environment environment) {
        final String envTarget = getValueOfProperty(environment, ENV_TARGET_KEY, "dev", Lists.newArrayList("dev"));
        logger.info("{} = {}", ENV_TARGET_KEY, envTarget);
    }

    private void logPersistenceTarget(final Environment environment) {
        final String envTarget = getValueOfProperty(environment, PERSISTENCE_TARGET_KEY, "h2", Lists.newArrayList("h2", "mysql", "cargo"));
        logger.info("{} = {}", PERSISTENCE_TARGET_KEY, envTarget);
    }

    private void logActiveSpringProfile(final Environment environment) {
        final String activeSpringProfile = getValueOfProperty(environment, ACTIVE_SPRING_PROFILE_KEY, "none", null);
        logger.info("{} = {}", ACTIVE_SPRING_PROFILE_KEY, activeSpringProfile);
    }

    private void logPersistenceData(final Environment environment) {
        final String persistenceHost = getValueOfProperty(environment, PERSISTENCE_HOST_KEY, "not-found", null);
        logger.info("{} = {}", PERSISTENCE_HOST_KEY, persistenceHost);
    }

    //

    private final String getValueOfProperty(final Environment environment, final String propertyKey, final String propertyDefaultValue, final List<String> acceptablePropertyValues) {
        String propValue = environment.getProperty(propertyKey);
        if (propValue == null) {
            propValue = propertyDefaultValue;
            logger.info("The {} doesn't have an explicit value; default value is = {}", propertyKey, propertyDefaultValue);
        }

        if (acceptablePropertyValues != null) {
            if (!acceptablePropertyValues.contains(propValue)) {
                logger.warn("The property = {} has an invalid value = {}", propertyKey, propValue);
            }
        }

        if (propValue == null) {
            logger.warn("The property = {} is null", propertyKey);
        }

        return propValue;
    }

}
