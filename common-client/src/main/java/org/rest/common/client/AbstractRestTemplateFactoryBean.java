package org.rest.common.client;

import org.apache.http.HttpHost;
import org.rest.common.security.DigestHttpComponentsClientHttpRequestFactory;
import org.rest.common.security.PreemptiveAuthHttpRequestFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestTemplateFactoryBean implements FactoryBean<RestTemplate>, InitializingBean {
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    public AbstractRestTemplateFactoryBean() {
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
        final HttpComponentsClientHttpRequestFactory requestFactory;
        final int timeout = env.getProperty("http.req.timeout", Integer.class);
        if (env.getProperty("sec.auth.basic", Boolean.class)) {
            final int port = env.getProperty(getPortPropertyName(), Integer.class);
            final String host = env.getProperty(getHostPropertyName());
            requestFactory = new PreemptiveAuthHttpRequestFactory(host, port, HttpHost.DEFAULT_SCHEME_NAME);
            requestFactory.setReadTimeout(timeout);
        } else {
            requestFactory = new DigestHttpComponentsClientHttpRequestFactory() {
                {
                    setReadTimeout(timeout);
                }
            };
        }
        restTemplate = new RestTemplate(requestFactory);

        restTemplate.getMessageConverters().remove(5); // removing the Jaxb2RootElementHttpMessageConverter

        final MarshallingHttpMessageConverter marshallingHttpMessageConverter = marshallingHttpMessageConverter();
        if (marshallingHttpMessageConverter != null) {
            restTemplate.getMessageConverters().add(marshallingHttpMessageConverter);
        }
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    //

    protected abstract MarshallingHttpMessageConverter marshallingHttpMessageConverter();

    protected String getHostPropertyName() {
        return "http.host";
    }

    protected String getPortPropertyName() {
        return "http.port";
    }

}
