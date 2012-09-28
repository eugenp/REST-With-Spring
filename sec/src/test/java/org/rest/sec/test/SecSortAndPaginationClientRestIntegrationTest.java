package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.client.AbstractSortAndPaginationClientRestIntegrationTest;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecSortAndPaginationClientRestIntegrationTest<T extends INameableEntity> extends AbstractSortAndPaginationClientRestIntegrationTest<T> {

    public SecSortAndPaginationClientRestIntegrationTest() {
        super();
    }

}
