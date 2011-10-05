package org.rest.test.integration.hello;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rest.model.Hello;
import org.rest.test.integration.common.MarshallingUtil;
import org.rest.test.integration.common.RESTPaths;
import org.rest.test.integration.common.RESTTemplate;

public class HelloRestAPI_IntegrationTest{
	private DefaultHttpClient httpClient;
	
	@Before
	public final void before(){
		this.httpClient = new DefaultHttpClient();
	}
	
	// GET
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH + "/" + randomNumeric( 4 ) );
		
		// When
		this.httpClient.execute( request );
		
		// Then
	}
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH + "/" + randomNumeric( 4 ) );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceForIdDoesExist_whenResourceOfThatIdIsRetrieved_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = RESTTemplate.createHello();
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH + "/" + idOfNewResource );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdDoesExist_whenResourceOfThatIdIsRetrieved_thenNameWasCorrectlyPersisted() throws ClientProtocolException, IOException{
		// Given
		final String nameOfNewResource = randomAlphabetic( 6 );
		final long idOfNewResource = RESTTemplate.createHello( new Hello( nameOfNewResource ) );
		
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH + "/" + idOfNewResource );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		final Hello resourceFromServer = MarshallingUtil.retrieveResourceFromResponse( response, Hello.class );
		Assert.assertEquals( nameOfNewResource, resourceFromServer.getName() );
	}
	@Test
	public final void givenResourceForIdDoesExist_whenResourceOfThatIdIsRetrieved_thenResourceWasCorrectlyPersisted() throws ClientProtocolException, IOException{
		// Given
		final Hello unpersistedResource = new Hello( randomAlphabetic( 6 ) );
		final long idOfNewResource = RESTTemplate.createHello( unpersistedResource );
		
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH + "/" + idOfNewResource );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		final Hello resourceFromServer = MarshallingUtil.retrieveResourceFromResponse( response, Hello.class );
		Assert.assertEquals( unpersistedResource, resourceFromServer );
	}
	
	// GET (all)
	
	@Test
	public final void whenResourcesAreRetrieved_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH );
		
		// When
		this.httpClient.execute( request );
		
		// Then
	}
	@Test
	public final void whenResourcesAreRetrieved_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}
	@SuppressWarnings( "rawtypes" )
	@Test
	public final void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() throws ClientProtocolException, IOException{
		// Given
		RESTTemplate.createHello();
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		final String resourcesAsJson = MarshallingUtil.retrieveJsonFromResponse( response );
		final List resources = MarshallingUtil.convertJsonToObject( resourcesAsJson, List.class );
		
		// Then
		assertFalse( resources.isEmpty() );
	}
	
	// POST
	
	@Test
	public final void whenResourceIsCreated_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( RESTPaths.HELLO_WORLD_PATH );
		final String localResourceAsJson = MarshallingUtil.convertResourceToJson( new Hello( randomAlphabetic( 6 ) ) );
		MarshallingUtil.decorateJsonRequest( request, localResourceAsJson );
		
		// When
		this.httpClient.execute( request );
		
		// Then
	}
	@Test
	public final void whenResourceIsCreated_then201IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( RESTPaths.HELLO_WORLD_PATH );
		final String localResourceAsJson = MarshallingUtil.convertResourceToJson( new Hello( randomAlphabetic( 6 ) ) );
		MarshallingUtil.decorateJsonRequest( request, localResourceAsJson );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 201 ) );
	}
	@Test
	public final void whenResourceIsCreated_thenIdOfNewResourceIsReturnedToTheClient() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( RESTPaths.HELLO_WORLD_PATH );
		final String localResourceAsJson = MarshallingUtil.convertResourceToJson( new Hello( randomAlphabetic( 6 ) ) );
		MarshallingUtil.decorateJsonRequest( request, localResourceAsJson );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		final long idOfNewResource = MarshallingUtil.extractIdFromCreateResponse( response );
		assertTrue( idOfNewResource > 0 );
	}
	
	// PUT
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = RESTTemplate.createHello();
		final String helloAsJson = RESTTemplate.getHello( idOfNewResource );
		
		final HttpPut request = new HttpPut( RESTPaths.HELLO_WORLD_PATH );
		MarshallingUtil.decorateJsonRequest( request, helloAsJson );
		
		// When
		this.httpClient.execute( request );
		
		// Then
	}
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = RESTTemplate.createHello();
		final String helloAsJson = RESTTemplate.getHello( idOfNewResource );
		
		final HttpPut request = new HttpPut( RESTPaths.HELLO_WORLD_PATH );
		MarshallingUtil.decorateJsonRequest( request, helloAsJson );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}
	
	// DELETE
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpDelete( RESTPaths.HELLO_WORLD_PATH + "/" + randomNumeric( 4 ) );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 404 ) );
	}
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = RESTTemplate.createHello();
		final HttpUriRequest request = new HttpDelete( RESTPaths.HELLO_WORLD_PATH + "/" + idOfNewResource );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}
	
}
