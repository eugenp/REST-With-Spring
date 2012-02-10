package org.rest.security;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.rest.sec.util.SecurityConstants.ADMIN_PASSWORD;
import static org.rest.sec.util.SecurityConstants.ADMIN_USERNAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.testing.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class AuthenticationRESTIntegrationTest{
	
	@Autowired
	ExamplePaths paths;
	
	// tests
	
	@Test
	public final void givenCorrectAuthenticationCredentialsSent_whenAuthenticationResourceIsCreated_then201IsReceived(){
		// Given
		// When
		final Response response = given().auth().digest( ADMIN_USERNAME, ADMIN_PASSWORD ).contentType( MediaType.APPLICATION_XML.toString() ).get( paths.getAuthenticationUri() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
}
