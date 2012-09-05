package org.rest.sec.client;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.common.security.DigestHttpComponentsClientHttpRequestFactory;
import org.rest.common.security.PreemptiveAuthHttpRequestFactory;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.model.dto.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thoughtworks.xstream.XStream;

@Component
@Profile("client")
public class RestTemplateFactoryBean implements FactoryBean<RestTemplate>, InitializingBean {
    private RestTemplate restTemplate;

    @Value("${sec.auth.basic}")
    boolean basicAuth;
    @Value("${http.req.timeout}")
    int timeout;

    @Value("${http.host}")
    private String host;
    @Value("${http.port}")
    private int port;

    public RestTemplateFactoryBean() {
        super();
    }

    // API

    @Override
    public RestTemplate getObject() {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        final HttpComponentsClientHttpRequestFactory requestFactory;
        if (basicAuth) {
            requestFactory = new PreemptiveAuthHttpRequestFactory(host, port, HttpHost.DEFAULT_SCHEME_NAME);
            requestFactory.setReadTimeout(timeout);
        } else {
            requestFactory = new DigestHttpComponentsClientHttpRequestFactory(httpClient) {
                {
                    setReadTimeout(timeout);
                }
            };
        }
        restTemplate = new RestTemplate(requestFactory);

        restTemplate.getMessageConverters().remove(5); // removing the Jaxb2RootElementHttpMessageConverter
        restTemplate.getMessageConverters().add(marshallingHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    //

    final MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
        marshallingHttpMessageConverter.setMarshaller(xstreamMarshaller());
        marshallingHttpMessageConverter.setUnmarshaller(xstreamMarshaller());

        return marshallingHttpMessageConverter;
    }

    final XStreamMarshaller xstreamMarshaller() {
        final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);
        xStreamMarshaller.setMode(XStream.NO_REFERENCES);
        xStreamMarshaller.setAnnotatedClasses(new Class[] { User.class, Principal.class, Role.class, Privilege.class });
        xStreamMarshaller.getXStream().addDefaultImplementation(java.sql.Timestamp.class, java.util.Date.class);

        return xStreamMarshaller;
    }

}
