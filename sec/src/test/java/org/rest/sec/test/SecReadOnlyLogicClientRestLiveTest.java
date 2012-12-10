package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.client.AbstractReadOnlyLogicClientLiveTest;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.rest.sec.spring.SecCommonApiConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfig.class, ClientTestConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecReadOnlyLogicClientRestLiveTest<T extends INameableEntity> extends AbstractReadOnlyLogicClientLiveTest<T> {

    //

}
