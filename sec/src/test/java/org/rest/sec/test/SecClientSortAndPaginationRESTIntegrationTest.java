package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.client.AbstractClientSortAndPaginationRESTIntegrationTest;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecClientSortAndPaginationRESTIntegrationTest<T extends INameableEntity> extends AbstractClientSortAndPaginationRESTIntegrationTest<T> {

    public SecClientSortAndPaginationRESTIntegrationTest() {
        super();
    }

}
