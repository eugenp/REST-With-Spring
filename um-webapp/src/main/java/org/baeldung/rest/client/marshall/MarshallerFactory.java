package org.baeldung.rest.client.marshall;

import static org.baeldung.rest.common.spring.util.CommonSpringProfileUtil.DEPLOYED;

import org.baeldung.rest.common.client.marshall.IMarshaller;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(DEPLOYED)
public class MarshallerFactory implements FactoryBean<IMarshaller> {

    public MarshallerFactory() {
        super();
    }

    // API

    @Override
    public IMarshaller getObject() {
        return new ProdJacksonMarshaller();
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
