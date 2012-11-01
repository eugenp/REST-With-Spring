package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.web.base.AbstractMimeRestLiveTest;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.rest.sec.spring.SecCommonApiConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecMimeRestLiveTest<T extends IEntity> extends AbstractMimeRestLiveTest<T> {

    public SecMimeRestLiveTest() {
        super();
    }

}
