package org.baeldung.rest.service.search;

import org.baeldung.rest.common.persistence.model.INameableEntity;
import org.baeldung.rest.common.service.search.AbstractSearchIntegrationTest;
import org.baeldung.rest.spring.ContextConfig;
import org.baeldung.rest.spring.PersistenceJpaConfig;
import org.baeldung.rest.spring.SecCommonApiConfig;
import org.baeldung.rest.spring.ServiceConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJpaConfig.class, ServiceConfig.class, ContextConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecSearchIntegrationTest<T extends INameableEntity> extends AbstractSearchIntegrationTest<T> {

    //

}
