package com.baeldung.um.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class UmServletConfig {

    @Bean
    @Order(1)
    public FilterRegistrationBean springSecurityFilterChain() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        final DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        filterRegBean.setFilter(delegatingFilterProxy);
        final List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        filterRegBean.setUrlPatterns(urlPatterns);
        return filterRegBean;
    }

}
