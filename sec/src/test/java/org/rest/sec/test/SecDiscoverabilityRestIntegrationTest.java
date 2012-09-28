package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.web.base.AbstractDiscoverabilityRestIntegrationTest;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecDiscoverabilityRestIntegrationTest<T extends IEntity> extends AbstractDiscoverabilityRestIntegrationTest<T> {

    public SecDiscoverabilityRestIntegrationTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
