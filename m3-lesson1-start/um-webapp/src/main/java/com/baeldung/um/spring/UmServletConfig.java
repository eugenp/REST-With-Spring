package com.baeldung.um.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
public class UmServletConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/api/*");

        final Map<String, String> params = new HashMap<>();
        params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        params.put("contextConfigLocation", "org.spring.sec2.spring");
        params.put("dispatchOptionsRequest", "true");
        registration.setInitParameters(params);

        registration.setLoadOnStartup(1);
        return registration;
    }

    /* @Bean
    @Order(1)
    public FilterRegistrationBean springSecurityFilterChain() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        final DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        filterRegBean.setFilter(delegatingFilterProxy);
        final List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        filterRegBean.setUrlPatterns(urlPatterns);
        return filterRegBean;
    }*/

}
