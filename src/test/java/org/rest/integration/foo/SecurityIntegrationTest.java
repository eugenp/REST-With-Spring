package org.rest.integration.foo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.integration.security.SecurityComponent;
import org.rest.spring.root.ApplicationConfig;
import org.rest.spring.root.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class },loader = AnnotationConfigContextLoader.class )
public class SecurityIntegrationTest{
	
	@Autowired
	SecurityComponent securityComponent;
	
	// tests

	@Test
	public final void givenUnauthenticated_whenAuthenticationIsPerformed_thenNoExceptions(){
		final String cookie = this.securityComponent.authenticateAsAdmin();
		
		assertNotNull( cookie );
	}
	
}
