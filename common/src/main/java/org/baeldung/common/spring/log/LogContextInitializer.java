package org.baeldung.common.spring.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 
 * <p>Makes the web application name and context available as MDC (log context) information to improve logging interpretation.</p>
 * <p>This is set in the parent thread and seems to be inherited by all children threads -i.e. the threads that handle the user requests.</p>
 * <p>It also logs web application startup and shutdown events.</p>
 *
 */
public class LogContextInitializer implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    // API

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        // Logging context information
        MDC.put("appName", sce.getServletContext().getServletContextName());
        MDC.put("contextPath", sce.getServletContext().getContextPath());

        if (logger.isInfoEnabled()) {
            logger.info("INITIALIZING APPLICATION \"{}\" ON WEB CONTEXT \"{}\".", sce.getServletContext().getServletContextName(), sce.getServletContext().getContextPath());
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        if (logger.isInfoEnabled()) {
            logger.info("DESTROYING APPLICATION \"{}\" ON WEB CONTEXT \"{}\".", sce.getServletContext().getServletContextName(), sce.getServletContext().getContextPath());
        }
    }

}
