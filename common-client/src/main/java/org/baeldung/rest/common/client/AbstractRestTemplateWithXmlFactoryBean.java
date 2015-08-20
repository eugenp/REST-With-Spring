package org.baeldung.rest.common.client;

import org.baeldung.rest.common.client.AbstractRestTemplateFactoryBean;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;

public abstract class AbstractRestTemplateWithXmlFactoryBean extends AbstractRestTemplateFactoryBean {

    @Override
    protected MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
        marshallingHttpMessageConverter.setMarshaller(xstreamMarshaller());
        marshallingHttpMessageConverter.setUnmarshaller(xstreamMarshaller());

        return marshallingHttpMessageConverter;
    }

    protected abstract XStreamMarshaller xstreamMarshaller();

}
