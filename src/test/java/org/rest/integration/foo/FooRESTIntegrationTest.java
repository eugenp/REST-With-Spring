package org.rest.integration.foo;

import static com.jayway.restassured.RestAssured.get;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.junit.Assert;
import org.junit.Ignore;
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

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@ActiveProfiles( "jpa" )
public class FooRESTIntegrationTest{
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplate restTemplate;
	
	@Autowired
	SecurityComponent securityComponent;
	
	// tests
	
	// GET
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions(){
		RestAssured.get( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );
	}
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived(){
		final Response response = this.givenAuthenticated().get( this.paths.getFooURL() + "/" + randomNumeric( 6 ) );
		
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenNameWasCorrectlyRetrieved(){
		// Given
		final String nameOfNewResource = randomAlphabetic( 6 );
		final String uriForResourceCreation = this.restTemplate.createResource( new Foo( nameOfNewResource ) );
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriForResourceCreation );
		
		// Then
		final JsonPath jp = new JsonPath( res.asString() );
		assertEquals( nameOfNewResource, jp.get( "name" ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedById_thenRetrievedResourceIsCorrect(){
		// Given
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final String uriForResourceCreation = this.restTemplate.createResource( unpersistedResource );
		
		// When
		final Response res = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriForResourceCreation );
		
		// Then
		final Foo resourceFromServer = res.body().as( Foo.class );
		Assert.assertEquals( unpersistedResource, resourceFromServer );
	}
	
	// GET (all)
	
	@Test
	public final void whenResourcesAreRetrieved_thenNoExceptions(){
		get( this.paths.getFooURL() );
	}
	@Test
	public final void whenResourcesAreRetrieved_then200IsReceived(){
		// When
		final Response response = this.givenAuthenticated().get( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	@SuppressWarnings( "rawtypes" )
	@Test
	public final void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved(){
		// Given
		this.restTemplate.createResource();
		
		// When
		final Response response = this.givenAuthenticated().get( this.paths.getFooURL() );
		
		// Then
		final List resources = response.body().as( List.class );
		assertFalse( resources.isEmpty() );
	}
	
	// POST
	
	@Test
	public final void whenAResourceIsCreated_thenNoExceptions(){
		this.givenAuthenticated().body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
	}
	@Test
	public final void whenAResourceIsCreated_then201IsReceived(){
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	@Test
	public final void whenNullResourceIsCreated_then415IsReceived(){
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	@Test
	public final void whenAResourceIsCreated_thenALocationIsReturnedToTheClient(){
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
		
		// Then
		assertNotNull( response.getHeader( HttpHeaders.LOCATION ) );
	}
	
	// PUT
	
	@Test
	public final void whenPutIsDoneOnInvalidResource_then400IsReceived(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplate.getResourceAsJson( uriForResourceCreation + "123" );
		
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( resourceAsJson ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 400 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenNoExceptions(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplate.getResourceAsJson( uriForResourceCreation );
		
		// When
		this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( resourceAsJson ).put( this.paths.getFooURL() );
	}
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplate.getResourceAsJson( uriForResourceCreation );
		
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( resourceAsJson ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	@Test
	@Ignore
	public final void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted(){
		// TODO
	}
	
	@Test
	public final void whenNullResourceIsUpdated_then415IsReceived(){
		// Given
		
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived(){
		// Given
		final Foo unpersistedEntity = new Foo( "new name" );
		unpersistedEntity.setId( new Long( randomNumeric( 6 ) ) );
		
		// When
		final Response response = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( unpersistedEntity ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	// DELETE
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived(){
		// When
		final Response response = this.givenAuthenticated().delete( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_then204IsReceived(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		
		// When
		final Response response = this.givenAuthenticated().delete( uriForResourceCreation );
		
		// Then
		assertThat( response.getStatusCode(), is( 204 ) );
	}
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable(){
		// Given
		final String uriForResourceCreation = this.restTemplate.createResource();
		this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).delete( uriForResourceCreation );
		
		// When
		final Response getResponse = this.givenAuthenticated().get( uriForResourceCreation );
		
		// Then
		assertThat( getResponse.getStatusCode(), is( 404 ) );
	}
	
	// util
	
	private final RequestSpecification givenAuthenticated(){
		return this.securityComponent.givenAuthenticatedByBasicAuth();
	}
	
}
