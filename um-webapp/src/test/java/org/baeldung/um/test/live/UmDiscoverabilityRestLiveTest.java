package org.baeldung.um.test.live;

import org.baeldung.common.interfaces.IDto;
import org.baeldung.test.common.web.AbstractDiscoverabilityLiveTest;
import org.baeldung.um.spring.UmContextConfig;
import org.baeldung.um.spring.UmClientConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmContextConfig.class, UmClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class UmDiscoverabilityRestLiveTest<T extends IDto> extends AbstractDiscoverabilityLiveTest<T> {

    public UmDiscoverabilityRestLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
