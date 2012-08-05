package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.common.persistence.model.IEntity;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecPersistenceServiceIntegrationTest<T extends IEntity> extends AbstractPersistenceServiceIntegrationTest<T> {

    //

}
