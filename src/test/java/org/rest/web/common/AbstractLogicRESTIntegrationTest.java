package org.rest.web.common;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.marshaller.IMarshaller;
import org.rest.testing.template.IRESTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@SuppressWarnings( "unchecked" )
public abstract class AbstractLogicRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	protected final Class< T > clazz;
	
	@Autowired
	@Qualifier( "xstreamMarshaller" )
	protected IMarshaller marshaller;
	
	public AbstractLogicRESTIntegrationTest( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// tests
	
	// GET
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions(){
		getTemplate().getResourceAsResponse( this.getURI() + "/" + randomNumeric( 4 ) );
	}
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived(){
		final Response response = getTemplate().getResourceAsResponse( this.getURI() + "/" + randomNumeric( 6 ) );
		
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved(){
		// Given
		final String uriForResourceCreation = this.getTemplate().createResourceAsURI();
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenResourceIsCorrectlyRetrieved(){
		// Given
		final T resource = this.createNewEntity();
		final String uriForResourceCreation = this.getTemplate().createResourceAsURI( resource );
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation );
		
		// Then
		final T retrievedResource = this.marshaller.decode( res.asString(), clazz );
		assertEquals( resource, retrievedResource );
	}
	
	// GET (all)
	
	@Test
	public final void whenResourcesAreRetrieved_thenNoExceptions(){
		getTemplate().getResourceAsResponse( this.getURI() );
	}
	
	@Test
	public final void whenResourcesAreRetrieved_then200IsReceived(){
		// When
		final Response response = this.givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( this.getURI() );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved(){
		// Given
		this.getTemplate().createResourceAsURI();
		
		// When
		final Response response = getTemplate().getResourceAsResponse( this.getURI() );
		
		// Then
		final List< T > allResources = marshaller.decode( response.asString(), List.class );
		assertThat( allResources, not( Matchers.<T> empty() ) );
	}
	
	// POST
	
	@Test
	public final void whenAResourceIsCreated_thenNoExceptions(){
		getTemplate().createResourceAsResponse( this.createNewEntity() );
	}
	
	@Test
	public final void whenAResourceIsCreated_then201IsReceived(){
		// When
		final Response response = getTemplate().createResourceAsResponse( this.createNewEntity() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
	@Test
	public final void whenNullResourceIsCreated_then415IsReceived(){
		// When
		final Response response = this.givenAuthenticated().contentType( marshaller.getMime() ).post( this.getURI() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	@Test
	public final void whenAResourceIsCreatedWithNonNullId_then409IsReceived(){
		final T resourceWithId = this.createNewEntity();
		resourceWithId.setId( 5l );
		
		// When
		final Response response = getTemplate().createResourceAsResponse( resourceWithId );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenAResourceIsCreated_thenALocationIsReturnedToTheClient(){
		// When
		final Response response = getTemplate().createResourceAsResponse( this.createNewEntity() );
		
		// Then
		assertNotNull( response.getHeader( HttpHeaders.LOCATION ) );
	}
	
	@Test
	@Ignore( "this will not always pass at this time" )
	public final void givenResourceExists_whenResourceWithSameAttributesIsCreated_then409IsReceived(){
		// Given
		final T newEntity = this.getTemplate().createNewEntity();
		this.getTemplate().createResourceAsURI( newEntity );
		
		// When
		final Response response = getTemplate().createResourceAsResponse( newEntity );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	// PUT
	
	@Test
	// @Ignore("some resource may not have an invalid state")
	public final void whenPutIsDoneOnInvalidResource_then409ConflictIsReceived(){
		// Given
		final T existingResource = this.getTemplate().createResourceAndGetAsEntity();
		makeInvalid( existingResource );
		
		// When
		final Response response = getTemplate().updateResourceAsResponse( existingResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenAResourceIsUpdatedWithNullId_then409IsReceived(){
		// When
		final Response response = getTemplate().updateResourceAsResponse( createNewEntity() );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenNoExceptions(){
		// Given
		final T resourceFromServer = this.getTemplate().createResourceAndGetAsEntity();
		
		// When
		getTemplate().updateResourceAsResponse( resourceFromServer );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived(){
		// Given
		final String resourceAsString = this.getTemplate().createResourceAndGetAsMime( marshaller.getMime() );
		
		// When
		final Response response = getTemplate().updateResourceAsResponse( resourceAsString );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void whenNullResourceIsUpdated_then415IsReceived(){
		// Given
		// When
		final Response response = this.givenAuthenticated().contentType( marshaller.getMime() ).put( this.getURI() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived(){
		// Given
		final T unpersistedEntity = this.createNewEntity();
		unpersistedEntity.setId( new Long( randomNumeric( 6 ) ) );
		
		// When
		final Response response = getTemplate().updateResourceAsResponse( unpersistedEntity );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted(){
		// Given
		final String uriForResourceCreation = this.getTemplate().createResourceAsURI();
		final T createdResourceFromServer = this.getTemplate().getResourceAsEntity( uriForResourceCreation );
		
		// When
		this.change( createdResourceFromServer );
		getTemplate().updateResourceAsResponse( createdResourceFromServer );
		
		final String updatedResourceAsString = this.getTemplate().getResourceAsMime( uriForResourceCreation, marshaller.getMime() );
		final T updatedResourceFromServer = this.marshaller.decode( updatedResourceAsString, clazz );
		
		// Then
		assertEquals( createdResourceFromServer, updatedResourceFromServer );
	}
	
	// DELETE
	
	@Test
	public final void whenAResourceIsDeletedByIncorrectNonNumericId_then400IsReceived(){
		// When
		final Response response = this.getTemplate().delete( getURI() + randomAlphabetic( 6 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 400 ) );
	}
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived(){
		// When
		final Response response = this.getTemplate().delete( this.getURI() + randomNumeric( 6 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_then204IsReceived(){
		// Given
		final String uriForResourceCreation = this.getTemplate().createResourceAsURI();
		
		// When
		final Response response = this.getTemplate().delete( uriForResourceCreation );
		
		// Then
		assertThat( response.getStatusCode(), is( 204 ) );
	}
	
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable(){
		// Given
		final String uriOfResource = this.getTemplate().createResourceAsURI();
		this.getTemplate().delete( uriOfResource );
		
		// When
		final Response getResponse = getTemplate().getResourceAsResponse( uriOfResource );
		
		// Then
		assertThat( getResponse.getStatusCode(), is( 404 ) );
	}
	
	// template method
	
	protected abstract IRESTTemplate< T > getTemplate();
	
	protected abstract String getURI();
	
	protected abstract void change( final T resource );
	
	protected abstract void makeInvalid( final T resource );
	
	protected abstract T createNewEntity();
	
	protected abstract RequestSpecification givenAuthenticated();
	
}
