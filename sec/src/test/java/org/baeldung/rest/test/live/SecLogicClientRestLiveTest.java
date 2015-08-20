package org.baeldung.rest.test.live;

import org.baeldung.rest.common.client.AbstractLogicClientLiveTest;
import org.baeldung.rest.common.client.INameableDto;
import org.baeldung.rest.spring.ClientTestConfig;
import org.baeldung.rest.spring.ContextConfig;
import org.baeldung.rest.spring.SecCommonApiConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfig.class, ClientTestConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecLogicClientRestLiveTest<T extends INameableDto> extends AbstractLogicClientLiveTest<T> {

    //

}
