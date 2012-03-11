package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.IEntity;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.testing.TestingConfig;
import org.rest.web.common.AbstractDiscoverabilityRESTIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, TestingConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecDiscoverabilityRESTIntegrationTest<T extends IEntity> extends AbstractDiscoverabilityRESTIntegrationTest<T> {

    public SecDiscoverabilityRESTIntegrationTest(final Class<T> clazzToSet) {
	super(clazzToSet);
    }

}
