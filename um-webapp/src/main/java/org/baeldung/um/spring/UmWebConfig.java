package org.baeldung.um.spring;

import java.util.List;

import org.baeldung.um.persistence.model.Principal;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({ "org.baeldung.common.web", "org.baeldung.um.web" })
@EnableWebMvc
public class UmWebConfig extends WebMvcConfigurerAdapter {

    public UmWebConfig() {
        super();
    }

    // beans

    public XStreamMarshaller xstreamMarshaller() {
        final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);
        xStreamMarshaller.setAnnotatedClasses(new Class[] { Principal.class, UserDto.class, Role.class, Privilege.class });
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
        }

        super.configureMessageConverters(messageConverters);
    }

    // https://github.com/joshlong/spring-travel/blob/master/spring-travel/server/src/main/java/org/springframework/samples/travel/config/web/WebConfiguration.java

}
