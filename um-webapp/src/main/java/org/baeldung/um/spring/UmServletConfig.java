package org.baeldung.um.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class UmServletConfig {

    public UmServletConfig() {
        super();
    }

    // beans

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/api/*");

        final Map<String, String> params = new HashMap<String, String>();
        params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        params.put("contextConfigLocation", "org.spring.sec2.spring");
        params.put("dispatchOptionsRequest", "true");
        registration.setInitParameters(params);

        registration.setLoadOnStartup(1);
        return registration;
    }

}
