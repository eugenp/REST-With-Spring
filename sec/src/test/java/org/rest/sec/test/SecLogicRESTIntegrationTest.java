package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.web.base.AbstractLogicRESTIntegrationTest;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecLogicRESTIntegrationTest<T extends INameableEntity> extends AbstractLogicRESTIntegrationTest<T> {

    public SecLogicRESTIntegrationTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
