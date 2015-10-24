package org.baeldung.um.client.marshall;

import static org.baeldung.common.spring.util.Profiles.DEPLOYED;

import org.baeldung.client.marshall.IMarshaller;
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
