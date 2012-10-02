package org.rest.sec.spring;

import java.util.List;

import org.rest.sec.model.Principal;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.model.dto.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({ "org.rest.common.web", "org.rest.sec.web" })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

    // beans

    public XStreamMarshaller xstreamMarshaller() {
        final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);
        xStreamMarshaller.setAnnotatedClasses(new Class[] { Principal.class, User.class, Role.class, Privilege.class });
        xStreamMarshaller.getXStream().addDefaultImplementation(java.sql.Timestamp.class, java.util.Date.class);

        return xStreamMarshaller;
    }

    public MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
        final XStreamMarshaller xstreamMarshaller = xstreamMarshaller();
        marshallingHttpMessageConverter.setMarshaller(xstreamMarshaller);
        marshallingHttpMessageConverter.setUnmarshaller(xstreamMarshaller);

        return marshallingHttpMessageConverter;
    }

    // template

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(marshallingHttpMessageConverter());

        final ClassLoader classLoader = getClass().getClassLoader();
        if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)) {
            messageConverters.add(new MappingJackson2HttpMessageConverter());
        } else if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", classLoader)) {
            messageConverters.add(new MappingJacksonHttpMessageConverter());
        }

        super.configureMessageConverters(messageConverters);
    }

    // https://github.com/joshlong/spring-travel/blob/master/spring-travel/server/src/main/java/org/springframework/samples/travel/config/web/WebConfiguration.java

}
