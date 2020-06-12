package com.baeldung.um.service.main;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.common.persistence.model.INameableEntity;
import com.baeldung.test.common.service.AbstractServiceIntegrationTest;
import com.baeldung.um.run.UmApp;
import com.baeldung.um.spring.UmContextConfig;
import com.baeldung.um.spring.UmJavaSecurityConfig;
import com.baeldung.um.spring.UmPersistenceJpaConfig;
import com.baeldung.um.spring.UmServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { // @formatter:off
        UmApp.class,
        UmContextConfig.class,

        UmPersistenceJpaConfig.class,

        UmServiceConfig.class,
        UmJavaSecurityConfig.class
}, loader = AnnotationConfigContextLoader.class)  // @formatter:on
public abstract class SecServiceIntegrationTest<T extends INameableEntity> extends AbstractServiceIntegrationTest<T> {

    //

}
