package org.rest.web.common;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Random;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.template.IRESTTemplate;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractLogicRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	protected final Class< T > clazz;
	
	public AbstractLogicRESTIntegrationTest( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// tests
	
	// find - one
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions(){
		getTemplate().findOneAsResponse( this.getURI() + "/" + randomNumeric( 4 ) );
	}
	
	@Test
	public final void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived(){
		final Response response = getTemplate().findOneAsResponse( this.getURI() + "/" + randomNumeric( 6 ) );
		
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved(){
		// Given
		final String uriForResourceCreation = this.getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().findOneAsResponse( uriForResourceCreation );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenResourceIsCorrectlyRetrieved(){
		final T resource = this.createNewEntity();
		final T existingResource = this.getTemplate().create( resource );
		final T retrievedResource = this.getTemplate().findOne( existingResource.getId() );
		
		assertEquals( resource, retrievedResource );
	}
	
	@Test
	@Ignore( "this was written for a neo4j persistence engine, which treats null ids differently than Hibernate" )
	public final void whenResourceIsRetrievedByNegativeId_then409IsReceived(){
		// Given id is negative number
		Long id = new Random().nextLong();
		id = id < 0 ? id : id * -1;
		
		// When
		final Response res = getTemplate().findOneAsResponse( this.getURI() + "/" + id );
		
		// Then
		assertThat( res.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenResourceIsRetrievedByNonNumericId_then400IsReceived(){
		// Given id is non numeric
		final String id = randomAlphabetic( 6 );
		
		// When
		final Response res = getTemplate().findOneAsResponse( this.getURI() + "/" + id );
		
		// Then
		assertThat( res.getStatusCode(), is( 400 ) );
	}
	
	// GET (all)
	
	@Test
	public final void whenResourcesAreRetrieved_thenNoExceptions(){
		getTemplate().findOneAsResponse( this.getURI() );
	}
	
	@Test
	public final void whenResourcesAreRetrieved_then200IsReceived(){
		// When
		final Response response = getTemplate().findAllAsResponse();
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved(){
		// Given
		this.getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final List< T > allResources = getTemplate().findAll();
		
		// Then
		assertThat( allResources, not( Matchers.<T> empty() ) );
	}
	
	// POST
	
	@Test
	public final void whenAResourceIsCreated_thenNoExceptions(){
		getTemplate().createAsResponse( this.createNewEntity() );
	}
	
	@Test
	public final void whenAResourceIsCreated_then201IsReceived(){
		// When
		final Response response = getTemplate().createAsResponse( this.createNewEntity() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
	@Test
	public final void whenNullResourceIsCreated_then415IsReceived(){
		// When
		final Response response = this.givenAuthenticated().contentType( getTemplate().getMarshaller().getMime() ).post( this.getURI() ); // in case there is a problem with the following:
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	@Test
	public final void whenAResourceIsCreatedWithNonNullId_then409IsReceived(){
		final T resourceWithId = this.createNewEntity();
		resourceWithId.setId( 5l );
		
		// When
		final Response response = getTemplate().createAsResponse( resourceWithId );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenAResourceIsCreated_thenALocationIsReturnedToTheClient(){
		// When
		final Response response = getTemplate().createAsResponse( this.createNewEntity() );
		
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
		final Response response = getTemplate().createAsResponse( newEntity );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	// PUT
	
	@Test
	// @Ignore("some resource may not have an invalid state")
	public final void whenPutIsDoneOnInvalidResource_then409ConflictIsReceived(){
		// Given
		final T existingResource = this.getTemplate().create( getTemplate().createNewEntity() );
		invalidate( existingResource );
		
		// When
		final Response response = getTemplate().updateAsResponse( existingResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenAResourceIsUpdatedWithNullId_then409IsReceived(){
		// When
		final Response response = getTemplate().updateAsResponse( createNewEntity() );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenNoExceptions(){
		// Given
		final T existingResource = this.getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		getTemplate().updateAsResponse( existingResource );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived(){
		// Given
		final T existingResource = this.getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response response = getTemplate().updateAsResponse( existingResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void whenNullResourceIsUpdated_then415IsReceived(){
		// Given
		// When
		final Response response = this.givenAuthenticated().contentType( getTemplate().getMarshaller().getMime() ).put( this.getURI() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived(){
		// Given
		final T unpersistedEntity = this.createNewEntity();
		unpersistedEntity.setId( new Long( randomNumeric( 6 ) ) );
		
		// When
		final Response response = getTemplate().updateAsResponse( unpersistedEntity );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted(){
		// Given
		final T existingResource = this.getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		this.change( existingResource );
		getTemplate().update( existingResource );
		
		final T updatedResourceFromServer = getTemplate().findOne( existingResource.getId() );
		
		// Then
		assertEquals( existingResource, updatedResourceFromServer );
	}
	
	// DELETE
	
	@Test
	public final void whenAResourceIsDeletedByIncorrectNonNumericId_then400IsReceived(){
		// When
		final Response response = this.getTemplate().deleteAsResponse( getURI() + randomAlphabetic( 6 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 400 ) );
	}
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived(){
		// When
		final Response response = this.getTemplate().deleteAsResponse( this.getURI() + randomNumeric( 6 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_then204IsReceived(){
		// Given
		final String uriForResourceCreation = this.getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response response = this.getTemplate().deleteAsResponse( uriForResourceCreation );
		
		// Then
		assertThat( response.getStatusCode(), is( 204 ) );
	}
	
	@Test
	public final void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable(){
		// Given
		final String uriOfResource = this.getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		this.getTemplate().deleteAsResponse( uriOfResource );
		
		// When
		final Response getResponse = getTemplate().findOneAsResponse( uriOfResource );
		
		// Then
		assertThat( getResponse.getStatusCode(), is( 404 ) );
	}
	
	// template method
	
	protected abstract IRESTTemplate< T > getTemplate();
	protected abstract String getURI();
	protected abstract void change( final T resource );
	protected abstract void invalidate( final T resource );
	protected abstract T createNewEntity();
	protected abstract RequestSpecification givenAuthenticated();
	
}
