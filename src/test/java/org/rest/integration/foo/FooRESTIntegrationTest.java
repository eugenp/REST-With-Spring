package org.rest.integration.foo;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.internal.matchers.StringContains.containsString;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.constants.HttpConstants;
import org.rest.integration.ExamplePaths;
import org.rest.integration.FooRESTTemplate;
import org.rest.integration.FooRESTTemplateNew;
import org.rest.model.Foo;
import org.rest.spring.ApplicationConfig;
import org.rest.spring.PersistenceConfig;
import org.rest.util.json.ConvertUtil;
import org.rest.util.json.DecorateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class },loader = AnnotationConfigContextLoader.class )
public class FooRESTIntegrationTest{
	private DefaultHttpClient httpClient;
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplate restTemplate;
	
	@Autowired
	FooRESTTemplateNew restTemplateNew;
	
	@Before
	public final void before(){
		this.httpClient = new DefaultHttpClient();
	}
	
	// GET
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions(){
		RestAssured.get( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );
	}
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived(){
		RestAssured.expect().statusCode( 404 ).when().get( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		
		// When
		final Response res = get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenNameWasCorrectlyRetrieved() throws ClientProtocolException, IOException{
		// Given
		final String nameOfNewResource = randomAlphabetic( 6 );
		final long idOfNewResource = this.restTemplate.createResource( new Foo( nameOfNewResource ) );
		
		// When
		final Response res = given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		final JsonPath jp = new JsonPath( res.asString() );
		assertEquals( nameOfNewResource, jp.get( "name" ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedById_thenRetrievedResourceIsCorrect() throws ClientProtocolException, IOException{
		// Given
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final long idOfNewResource = this.restTemplate.createResource( unpersistedResource );
		
		// When
		final Response res = given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		final Foo resourceFromServer = ConvertUtil.convertJsonToResource( res.asString(), Foo.class );
		Assert.assertEquals( unpersistedResource, resourceFromServer );
	}
	@Test
	public final void givenRequestAcceptsJson_whenResourceIsRetrievedById_thenResponseContentTypeIsJson() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource( new Foo( randomAlphabetic( 6 ) ) );
		
		// When
		final Response res = given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		assertThat( res.getContentType(), containsString( HttpConstants.MIME_JSON ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsXML_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		
		// When
		final Response res = given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_XML ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenRequestAcceptsXML_whenResourceIsRetrievedById_thenResponseContentTypeIsXML() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource( new Foo( randomAlphabetic( 6 ) ) );
		
		// When
		final Response res = given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_XML ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		assertThat( res.getContentType(), containsString( HttpConstants.MIME_XML ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedByIdAsXML_thenRetrievedResourceIsCorrect() throws ClientProtocolException, IOException{
		// Given
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final long idOfNewResource = this.restTemplate.createResource( unpersistedResource );
		
		// When
		/*final Response res = */given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_XML ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		// TODO
	}
	
	// GET (all)
	
	@Test
	public final void whenResourcesAreRetrieved_thenNoExceptions(){
		get( this.paths.getFooURL() );
	}
	@Test
	public final void whenResourcesAreRetrieved_then200IsReceived(){
		// When
		final Response response = get( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	@SuppressWarnings( "rawtypes" )
	@Test
	public final void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() throws ClientProtocolException, IOException{
		// Given
		this.restTemplate.createResource();
		
		// When
		final Response response = get( this.paths.getFooURL() );
		
		// Then
		final List resources = ConvertUtil.convertJsonToResource( response.asString(), List.class );
		assertFalse( resources.isEmpty() );
	}
	
	// POST
	
	@Test
	public final void whenResourceIsCreated_thenNoExceptions(){
		given().body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
	}
	@Test
	public final void whenResourceIsCreated_then201IsReceived() throws IOException{
		// When
		final String resourceAsJson = ConvertUtil.convertResourceToJson( new Foo( randomAlphabetic( 6 ) ) );
		final Response response = given().contentType( HttpConstants.MIME_JSON ).body( resourceAsJson ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	@Test
	public final void whenResourceIsCreated_thenIdOfNewResourceIsReturnedToTheClient(){
		// When
		final Response response = given().contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( this.paths.getFooURL() );
		
		// Then
		final long idOfNewResource = Long.parseLong( response.asString() );
		assertTrue( idOfNewResource > 0 );
	}
	@Test
	public final void whenNullResourceIsCreated_then415IsReceived(){
		// When
		final Response response = given().contentType( HttpConstants.MIME_JSON ).post( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	// PUT
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplate.getResourceAsJson( idOfNewResource );
		
		// When
		given().contentType( HttpConstants.MIME_JSON ).body( resourceAsJson ).put( this.paths.getFooURL() );
	}
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplateNew.getResourceAsJson( idOfNewResource );
		
		// When
		final Response response = given().contentType( HttpConstants.MIME_JSON ).body( resourceAsJson ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	@Test
	// TODO
	public final void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final Foo createdEntity = this.restTemplate.getResourceViaJson( idOfNewResource, Foo.class );
		createdEntity.setName( "new name" );
		
		final HttpPut request = new HttpPut( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, createdEntity );
		
		// When
		this.httpClient.execute( request );
		
		// Then
		final Foo updatedEntity = this.restTemplate.getResourceViaJson( idOfNewResource, Foo.class );
		assertEquals( createdEntity.getName(), updatedEntity.getName() );
	}
	@Test
	public final void whenNullResourceIsUpdated_then415IsReceived(){
		// When
		final Response response = given().contentType( HttpConstants.MIME_JSON ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived(){
		// Given
		final Foo unpersistedEntity = new Foo( "new name" );
		unpersistedEntity.setId( 1000l );
		
		// When
		final Response response = given().contentType( HttpConstants.MIME_JSON ).body( unpersistedEntity ).put( this.paths.getFooURL() );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	// DELETE
	
	
	// DELETE
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived(){
		// When
		final Response response = given().delete( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		
		// When
		final Response response = given().delete( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		given().contentType( HttpConstants.MIME_JSON ).delete( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// When
		final Response getResponse = given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// Then
		assertThat( getResponse.getStatusCode(), is( 404 ) );
	}
	
}
