package org.baeldung.um.test.live;

import org.baeldung.common.interfaces.INameableDto;
import org.baeldung.test.common.web.AbstractReadOnlyLogicLiveTest;
import org.baeldung.um.spring.CommonTestConfig;
import org.baeldung.um.spring.UmClientConfig;
import org.baeldung.um.spring.UmContextConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmContextConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class UmReadOnlyLogicRestLiveTest<T extends INameableDto> extends AbstractReadOnlyLogicLiveTest<T> {

    public UmReadOnlyLogicRestLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
