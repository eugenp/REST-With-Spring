package org.rest.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.spring.ApplicationConfig;
import org.rest.spring.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class },loader = AnnotationConfigContextLoader.class )
public class SpringIntegrationTest{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplate restTemplate;
	
	// GET
	
	@Test
	public final void whenMinimalSpringContextIsInitializedForIntegrationTesting_thenNoExceptions(){
		
		// When
		
		// Then
	}
	
}
