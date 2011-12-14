package org.rest.integration.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.util.HttpConstants;
import org.rest.integration.ExamplePaths;
import org.rest.integration.FooRESTTemplate;
import org.rest.integration.security.SecurityComponent;
import org.rest.model.Foo;
import org.rest.spring.application.ApplicationConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@ActiveProfiles( "jpa" )
public class FooRESTMimeIntegrationTest{
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplate restTemplate;
	
	@Autowired
	SecurityComponent securityComponent;
	
	// tests
	
	// GET
	
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsJson_then200IsReceived(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriForResourceCreation );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsXML__then200IsReceived(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_XML ).get( uriForResourceCreation );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsAtom_then200IsReceived(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_ATOM ).get( uriForResourceCreation );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenRequestAcceptsJson_whenResourceIsRetrievedById_thenResponseContentTypeIsJson(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource( new Foo( randomAlphabetic( 6 ) ) );
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriForResourceCreation );
		
		// Then
		assertThat( res.getContentType(), containsString( HttpConstants.MIME_JSON ) );
	}
	@Test
	public final void givenRequestAcceptsXML_whenResourceIsRetrievedById__thenResponseContentTypeIsXML(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource( new Foo( randomAlphabetic( 6 ) ) );
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_XML ).get( uriForResourceCreation );
		
		// Then
		assertThat( res.getContentType(), containsString( HttpConstants.MIME_XML ) );
	}
	@Test
	public final void givenRequestAcceptsAtom_whenResourceIsRetrievedById_thenResponseContentTypeIsAtom(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource( new Foo( randomAlphabetic( 6 ) ) );
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_ATOM ).get( uriForResourceCreation );
		
		// Then
		assertThat( res.getContentType(), containsString( HttpConstants.MIME_ATOM ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedByIdAsXML_thenRetrievedResourceIsCorrect(){
		// Given
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final String uriForResourceCreation = this.restTemplate.createResource( unpersistedResource );
		
		// When
		/*final Response res = */this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_XML ).get( uriForResourceCreation );
		
		// Then
		// TODO
	}
	
	// util
	
	private final RequestSpecification givenAuthenticated(){
		return this.securityComponent.givenAuthenticatedByBasicAuth();
	}
	
}
