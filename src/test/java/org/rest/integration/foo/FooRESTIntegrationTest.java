package org.rest.integration.foo;

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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.integration.ExamplePaths;
import org.rest.integration.FooRESTTemplate;
import org.rest.model.Foo;
import org.rest.util.common.ExtractUtil;
import org.rest.util.http.HttpUtil;
import org.rest.util.json.DecorateUtil;
import org.rest.util.json.RetrieveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {// @formatter:off
	"classpath:/test_rest_scan.xml"
}) 
// @formatter:on
public class FooRESTIntegrationTest{
	private DefaultHttpClient httpClient;

	@Autowired
	ExamplePaths paths;

	@Autowired
	FooRESTTemplate restTemplate;

	@Before
	public final void before(){
		this.httpClient = new DefaultHttpClient();
	}

	// GET

	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );

		// When
		this.httpClient.execute( request );

		// Then
	}

	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 404 ) );
	}

	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + idOfNewResource );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}

	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenNameWasCorrectlyRetrieved() throws ClientProtocolException, IOException{
		// Given
		final String nameOfNewResource = randomAlphabetic( 6 );
		final long idOfNewResource = this.restTemplate.createResource( new Foo( nameOfNewResource ) );
		
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + idOfNewResource );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		final Foo resourceFromServer = RetrieveUtil.retrieveResourceFromResponse( response, Foo.class );
		Assert.assertEquals( nameOfNewResource, resourceFromServer.getName() );
	}

	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedById_thenRetrievedResourceIsCorrect() throws ClientProtocolException, IOException{
		// Given
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final long idOfNewResource = this.restTemplate.createResource( unpersistedResource );
		
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + idOfNewResource );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		final Foo resourceFromServer = RetrieveUtil.retrieveResourceFromResponse( response, Foo.class );
		Assert.assertEquals( unpersistedResource, resourceFromServer );
	}

	@Test
	public final void givenRequestAcceptsJson_whenResourceIsRetrievedById_thenResponseContentTypeIsJson() throws ClientProtocolException, IOException{
		// Given
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final long idOfNewResource = this.restTemplate.createResource( unpersistedResource );
		
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + idOfNewResource );
		
		// When
		final HttpResponse response = this.httpClient.execute( request );
		
		// Then
		final String valueOfContentTypeHeader = HttpUtil.getHeader( response, "Content-Type" );
		assertThat( valueOfContentTypeHeader, containsString( "application/json" ) );
	}

	// GET (all)

	@Test
	public final void whenResourcesAreRetrieved_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() );

		// When
		this.httpClient.execute( request );

		// Then
	}

	@Test
	public final void whenResourcesAreRetrieved_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}
	
	@SuppressWarnings( "rawtypes" )
	@Test
	public final void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved() throws ClientProtocolException, IOException{
		// Given
		this.restTemplate.createResource();
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() );

		// When
		final HttpResponse response = this.httpClient.execute( request );
		final List resources = RetrieveUtil.retrieveResourceFromResponse( response, List.class );

		// Then
		assertFalse( resources.isEmpty() );
	}

	// POST

	@Test
	public final void whenResourceIsCreated_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, new Foo( randomAlphabetic( 6 ) ) );

		// When
		this.httpClient.execute( request );

		// Then
	}

	@Test
	public final void whenResourceIsCreated_then201IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, new Foo( randomAlphabetic( 6 ) ) );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 201 ) );
	}

	@Test
	public final void whenResourceIsCreated_thenIdOfNewResourceIsReturnedToTheClient() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, new Foo( randomAlphabetic( 6 ) ) );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		final long idOfNewResource = ExtractUtil.extractIdFromCreateResponse( response );
		assertTrue( idOfNewResource > 0 );
	}

	@Test
	public final void whenNullResourceIsCreated_then415IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpPost request = new HttpPost( this.paths.getFooURL() );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 415 ) );
	}

	// PUT

	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplate.getResourceAsJson( idOfNewResource );
		
		final HttpPut request = new HttpPut( this.paths.getFooURL() );
		DecorateUtil.setJsonOnRequest( request, resourceAsJson );

		// When
		this.httpClient.execute( request );

		// Then
	}
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final String resourceAsJson = this.restTemplate.getResourceAsJson( idOfNewResource );
		
		final HttpEntityEnclosingRequestBase request = new HttpPut( this.paths.getFooURL() );
		DecorateUtil.setJsonOnRequest( request, resourceAsJson );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final Foo createdEntity = this.restTemplate.getResource( idOfNewResource, Foo.class );
		createdEntity.setName( "new name" );
		
		final HttpPut request = new HttpPut( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, createdEntity );

		// When
		this.httpClient.execute( request );

		// Then
		final Foo updatedEntity = this.restTemplate.getResource( idOfNewResource, Foo.class );
		assertEquals( createdEntity.getName(), updatedEntity.getName() );
	}

	@Test
	public final void whenNullResourceIsUpdated_then415IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpPut request = new HttpPut( this.paths.getFooURL() );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 415 ) );
	}

	@Test
	public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived() throws ClientProtocolException, IOException{
		// Given
		final Foo unpersistedEntity = new Foo( "new name" );
		unpersistedEntity.setId( 1000l );
		
		final HttpPut request = new HttpPut( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, unpersistedEntity );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 404 ) );
	}

	// DELETE

	@Test
	public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived() throws ClientProtocolException, IOException{
		// Given
		final HttpUriRequest request = new HttpDelete( this.paths.getFooURL() + "/" + randomNumeric( 4 ) );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 404 ) );
	}

	@Test
	public final void givenResourceExist_whenResourceIsDeleted_then200IsReceived() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final HttpUriRequest request = new HttpDelete( this.paths.getFooURL() + "/" + idOfNewResource );

		// When
		final HttpResponse response = this.httpClient.execute( request );

		// Then
		assertThat( response.getStatusLine().getStatusCode(), is( 200 ) );
	}

	@Test
	public final void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable() throws ClientProtocolException, IOException{
		// Given
		final long idOfNewResource = this.restTemplate.createResource();
		final HttpUriRequest request = new HttpDelete( this.paths.getFooURL() + "/" + idOfNewResource );
		this.httpClient.execute( request );

		// When
		final HttpUriRequest getRequest = new HttpGet( this.paths.getFooURL() + "/" + idOfNewResource );
		final HttpResponse getResponse = new DefaultHttpClient().execute( getRequest );

		// Then
		assertThat( getResponse.getStatusLine().getStatusCode(), is( 404 ) );
	}
	
}
