package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.test.AbstractRESTIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public abstract class AbstractSecRESTIntegrationTest extends AbstractRESTIntegrationTest{
	//
}
