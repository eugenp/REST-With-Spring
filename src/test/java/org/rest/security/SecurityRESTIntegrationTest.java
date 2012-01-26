package org.rest.security;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.poc.model.Foo;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class SecurityRESTIntegrationTest{
	
	@Autowired
	ExamplePaths paths;
	
	// tests
	
	@Test
	public final void givenUnauthenticated_whenAResourceIsCreated_then401IsReceived(){
		// Given
		// When
		final Response response = given().contentType( MediaType.APPLICATION_XML.toString() ).body( new Foo( randomAlphabetic( 6 ) ) ).post( paths.getFooUri() );
		
		// Then
		assertThat( response.getStatusCode(), is( 401 ) );
	}
	
	@Test
	public final void givenAuthenticatedByBasicAuth_whenAResourceIsCreated_then201IsReceived(){
		// Given
		// When
		final Response response = given().auth().preemptive().basic( SecurityUtil.ADMIN_USERNAME, SecurityUtil.ADMIN_PASSWORD ).contentType( MediaType.APPLICATION_XML.toString() ).body( new Foo( randomAlphabetic( 6 ) ) ).post( paths.getFooUri() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	@Test
	public final void givenAuthenticatedByDigestAuth_whenAResourceIsCreated_then201IsReceived(){
		// Given
		// When
		final Response response = given().auth().digest( SecurityUtil.ADMIN_USERNAME, SecurityUtil.ADMIN_PASSWORD ).contentType( MediaType.APPLICATION_XML.toString() ).body( new Foo( randomAlphabetic( 6 ) ) ).post( paths.getFooUri() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
}
