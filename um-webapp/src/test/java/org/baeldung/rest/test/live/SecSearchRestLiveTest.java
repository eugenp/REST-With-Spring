package org.baeldung.rest.test.live;

import org.baeldung.rest.common.client.INameableDto;
import org.baeldung.rest.common.web.AbstractSearchLiveTest;
import org.baeldung.rest.spring.ClientTestConfig;
import org.baeldung.rest.spring.ContextConfig;
import org.baeldung.rest.spring.SecCommonApiConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecSearchRestLiveTest<T extends INameableDto> extends AbstractSearchLiveTest<T> {

    public SecSearchRestLiveTest() {
        super();
    }

}
