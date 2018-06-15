package com.baeldung.um.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

public class UmStandaloneServletContainerConfig extends AbstractReactiveWebInitializer {

    private final static Class<?>[] CONFIGS = { // @formatter:off
            UmApp.class                        
    }; // @formatter:on        

    @Override
    protected Class<?>[] getConfigClasses() {

        return CONFIGS;
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext servletAppContext = new AnnotationConfigApplicationContext();
        Class<?>[] configClasses = getConfigClasses();
        if (!ObjectUtils.isEmpty(configClasses)) {
            servletAppContext.register(configClasses);
        }
        servletAppContext.getEnvironment().setActiveProfiles("deployed");
        return servletAppContext;
    }

}
