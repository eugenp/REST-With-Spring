package org.rest.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.spring.application.ApplicationConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.security.SecurityConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { PersistenceJPAConfig.class, SecurityConfig.class, ApplicationConfig.class },loader = AnnotationConfigContextLoader.class )
@ActiveProfiles( "jpa" )
public final class SpringTest{
	
	@Test
	public final void whenSpringContextIsInstantiated_thenNoExceptions(){
		//
	}
	
}
