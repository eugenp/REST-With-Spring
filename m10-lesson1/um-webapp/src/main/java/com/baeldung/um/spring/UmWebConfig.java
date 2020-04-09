package com.baeldung.um.spring;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({ "com.baeldung.common.web", "com.baeldung.um.web" })
@EnableWebMvc
public class UmWebConfig implements WebMvcConfigurer {

    public UmWebConfig() {
        super();
    }

    // beans

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(etagFilter());
        registration.addUrlPatterns("/*");
        registration.setName("etagFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "etagFilter")
    public Filter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
