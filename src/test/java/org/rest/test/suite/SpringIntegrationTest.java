package org.rest.test.suite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.security.SecurityConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { PersistenceJPAConfig.class, SecurityConfig.class, ApplicationTestConfig.class },loader = AnnotationConfigContextLoader.class )
public final class SpringIntegrationTest{
	
	@Test
	public final void whenSpringContextIsInstantiated_thenNoExceptions(){
		//
	}
	
}
