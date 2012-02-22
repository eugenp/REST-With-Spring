package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.test.AbstractRESTIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public abstract class AbstractSecRESTIntegrationTest extends AbstractRESTIntegrationTest{
	//
}
