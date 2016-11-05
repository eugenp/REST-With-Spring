package com.baeldung.um.spring;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.baeldung.um.web.KryoHttpMessageConverter;

@Configuration
@ComponentScan({ "com.baeldung.common.web", "com.baeldung.um.web" })
@EnableAspectJAutoProxy
public class UmWebConfig extends WebMvcConfigurationSupport {

    public UmWebConfig() {
        super();
    }

    // beans

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(new KryoHttpMessageConverter());
        // super.addDefaultHttpMessageConverters(messageConverters);
    }
}
