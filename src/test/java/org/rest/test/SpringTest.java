package org.rest.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.spring.ApplicationConfig;
import org.rest.spring.PersistenceConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class },loader = AnnotationConfigContextLoader.class )
public final class SpringTest{
	
	@Test
	public final void whenSpringContextIsInstantiated_thenNoExceptions(){
		// When
	}
	
}
