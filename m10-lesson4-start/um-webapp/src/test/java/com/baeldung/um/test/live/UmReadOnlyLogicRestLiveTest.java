package com.baeldung.um.test.live;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.common.interfaces.INameableDto;
import com.baeldung.test.common.web.AbstractReadOnlyLogicLiveTest;
import com.baeldung.um.spring.CommonTestConfig;
import com.baeldung.um.spring.UmClientConfig;
import com.baeldung.um.spring.UmLiveTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class UmReadOnlyLogicRestLiveTest<T extends INameableDto> extends AbstractReadOnlyLogicLiveTest<T> {

    public UmReadOnlyLogicRestLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
