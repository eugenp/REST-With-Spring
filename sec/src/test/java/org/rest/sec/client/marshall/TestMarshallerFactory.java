package org.rest.sec.client.marshall;

import static org.rest.common.spring.CommonSpringProfileUtil.TEST;

import org.rest.common.client.marshall.IMarshaller;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(TEST)
public class TestMarshallerFactory implements FactoryBean<IMarshaller> {

    public TestMarshallerFactory() {
        super();
    }

    // API

    @Override
    public IMarshaller getObject() {
        // return new XStreamMarshaller();
        return new JacksonMarshaller();
    }

    @Override
    public Class<IMarshaller> getObjectType() {
        return IMarshaller.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
