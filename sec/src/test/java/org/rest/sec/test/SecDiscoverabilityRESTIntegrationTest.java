package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.web.base.AbstractDiscoverabilityRESTIntegrationTest;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecDiscoverabilityRESTIntegrationTest<T extends IEntity> extends AbstractDiscoverabilityRESTIntegrationTest<T> {

    public SecDiscoverabilityRESTIntegrationTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
