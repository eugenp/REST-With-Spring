package org.rest.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.client.AuthenticationRESTTemplate;
import org.rest.client.UserRESTTemplate;
import org.rest.sec.dto.User;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.test.AbstractRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@SuppressWarnings( "rawtypes" )
@Ignore( "TODO: temporarily disabled" )
public class ClientRESTIntegrationTest extends AbstractRESTIntegrationTest{
	
	@Autowired
	private AuthenticationRESTTemplate authenticationRestTemplate;
	
	@Autowired
	private UserRESTTemplate userRESTTemplate;
	
	// tests
	
	// GET
	
	@Test
	public final void whenGetUsersWithAuthentication_then200IsReceived(){
		// When
		final ResponseEntity< List > response = userRESTTemplate.findAll();
		
		// Then
		assertThat( response.getStatusCode().value(), is( 200 ) );
	}
	
	@Test
	public final void whenAuthenticating_then201IsReceived(){
		// When
		final ResponseEntity< User > response = authenticationRestTemplate.authenticate();
		
		// Then
		assertThat( response.getStatusCode().value(), is( 201 ) );
	}
	
}
