package org.rest.integration.security;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.util.HttpConstants;
import org.rest.integration.ExamplePaths;
import org.rest.model.Foo;
import org.rest.spring.application.ApplicationConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@ActiveProfiles( "jpa" )
public class SecurityIntegrationTest{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	SecurityComponent securityComponent;
	
	// tests
	
	@Test
	public final void givenUnauthenticated_whenAResourceIsCreated_then401IsReceived(){
		// Given
		// When
		final Response response = given().contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 401 ) );
	}
	
	@Test
	public final void givenAuthenticatedByBasicAuth_whenAResourceIsCreated_then201IsReceived(){
		// Given
		// When
		final Response response = given().auth().preemptive().basic( SecurityComponent.ADMIN_USERNAME, SecurityComponent.ADMIN_PASSWORD ).contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	@Test
	public final void givenAuthenticatedByDigestAuth_whenAResourceIsCreated_then201IsReceived(){
		// Given
		// When
		final Response response = given().auth().digest( SecurityComponent.ADMIN_USERNAME, SecurityComponent.ADMIN_PASSWORD ).contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
}
