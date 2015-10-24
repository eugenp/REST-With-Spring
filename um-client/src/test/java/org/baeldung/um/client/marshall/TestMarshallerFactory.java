package org.baeldung.um.client.marshall;

import static org.baeldung.common.spring.util.Profiles.TEST;

import org.baeldung.client.marshall.IMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile(TEST)
public class TestMarshallerFactory implements FactoryBean<IMarshaller> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    public TestMarshallerFactory() {
        super();
    }

    // API

    @Override
    public IMarshaller getObject() {
        final String testMime = env.getProperty("test.mime");
        logger.info("Initializing Marshaller for mime = " + testMime);
        if (testMime != null) {
            switch (testMime) {
            case "json":
                return new JacksonMarshaller();
            // case "xml":
            // return new XStreamMarshaller();
            default:
                throw new IllegalStateException();
            }
        }

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
