package com.baeldung.um.service.main;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.common.persistence.model.INameableEntity;
import com.baeldung.test.common.service.AbstractServiceIntegrationTest;
import com.baeldung.um.spring.UmClientConfig;
import com.baeldung.um.spring.UmContextConfig;
import com.baeldung.um.spring.UmPersistenceJpaConfig;
import com.baeldung.um.spring.UmServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmPersistenceJpaConfig.class, UmServiceConfig.class, UmContextConfig.class, UmClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecServiceIntegrationTest<T extends INameableEntity> extends AbstractServiceIntegrationTest<T> {

    //

}
