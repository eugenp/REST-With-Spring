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
import org.rest.client.template.IRESTTemplate;
import org.rest.common.IEntity;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.util.IDUtils;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractLogicRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	protected final Class< T > clazz;
	
	public AbstractLogicRESTIntegrationTest( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		clazz = clazzToSet;
	}
	
	// tests
	
	// find - one
	
	@Test
	public void givenResourceForIdDoesNotExist_whenResourceIsRetrieved_thenNoExceptions(){
		getTemplate().findOneAsResponse( getURI() + "/" + randomNumeric( 4 ) );
	}
	
	@Test
	public void givenResourceForIdDoesNotExist_whenResourceOfThatIdIsRetrieved_then404IsReceived(){
		final Response response = getTemplate().findOneAsResponse( getURI() + "/" + randomNumeric( 6 ) );
		
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().findOneAsResponse( uriForResourceCreation );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_thenResourceIsCorrectlyRetrieved(){
		final T resource = getTemplate().createNewEntity();
		final T existingResource = getTemplate().create( resource );
		final T retrievedResource = getTemplate().findOne( existingResource.getId() );
		
		assertEquals( resource, retrievedResource );
	}
	
	@Test
	@Ignore( "this was written for a neo4j persistence engine, which treats null ids differently than Hibernate" )
	public void whenResourceIsRetrievedByNegativeId_then409IsReceived(){
		final Long id = IDUtils.randomNegativeLong();
		
		// When
		final Response res = getTemplate().findOneAsResponse( getURI() + "/" + id );
		
		// Then
		assertThat( res.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public void whenResourceIsRetrievedByNonNumericId_then400IsReceived(){
		// Given id is non numeric
		final String id = randomAlphabetic( 6 );
		
		// When
		final Response res = getTemplate().findOneAsResponse( getURI() + "/" + id );
		
		// Then
		assertThat( res.getStatusCode(), is( 400 ) );
	}
	
	// find - all
	
	@Test
	public void whenResourcesAreRetrieved_thenNoExceptions(){
		getTemplate().findOneAsResponse( getURI() );
	}
	
	@Test
	public void whenResourcesAreRetrieved_then200IsReceived(){
		// When
		final Response response = getTemplate().findAllAsResponse();
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public void whenResourcesAreRetrieved_thenResourcesAreCorrectlyRetrieved(){
		// Given
		getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final List< T > allResources = getTemplate().findAll();
		
		// Then
		assertThat( allResources, not( Matchers.<T> empty() ) );
	}
	
	// create
	
	@Test
	public void whenAResourceIsCreated_thenNoExceptions(){
		getTemplate().createAsResponse( getTemplate().createNewEntity() );
	}
	
	@Test
	public void whenAResourceIsCreated_then201IsReceived(){
		// When
		final Response response = getTemplate().createAsResponse( getTemplate().createNewEntity() );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
	@Test
	public void whenNullResourceIsCreated_then415IsReceived(){
		// When
		final Response response = givenAuthenticated().contentType( getTemplate().getMarshaller().getMime() ).post( getURI() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	@Test
	public void whenAResourceIsCreatedWithNonNullId_then409IsReceived(){
		final T resourceWithId = getTemplate().createNewEntity();
		resourceWithId.setId( 5l );
		
		// When
		final Response response = getTemplate().createAsResponse( resourceWithId );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public void whenAResourceIsCreated_thenALocationIsReturnedToTheClient(){
		// When
		final Response response = getTemplate().createAsResponse( getTemplate().createNewEntity() );
		
		// Then
		assertNotNull( response.getHeader( HttpHeaders.LOCATION ) );
	}
	
	@Test
	@Ignore( "this will not always pass at this time" )
	public void givenResourceExists_whenResourceWithSameAttributesIsCreated_then409IsReceived(){
		// Given
		final T newEntity = getTemplate().createNewEntity();
		getTemplate().createResourceAsURI( newEntity );
		
		// When
		final Response response = getTemplate().createAsResponse( newEntity );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	// update
	
	@Test
	// @Ignore("some resource may not have an invalid state")
	public void givenInvalidResource_whenResourceIsUpdated_then409ConflictIsReceived(){
		// Given
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().invalidate( existingResource );
		
		// When
		final Response response = getTemplate().updateAsResponse( existingResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public void whenAResourceIsUpdatedWithNullId_then409IsReceived(){
		// When
		final Response response = getTemplate().updateAsResponse( getTemplate().createNewEntity() );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions(){
		// Given
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		getTemplate().updateAsResponse( existingResource );
	}
	
	@Test
	public void givenResourceExists_whenResourceIsUpdated_then200IsReceived(){
		// Given
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response response = getTemplate().updateAsResponse( existingResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public void whenNullResourceIsUpdated_then415IsReceived(){
		// Given
		// When
		final Response response = givenAuthenticated().contentType( getTemplate().getMarshaller().getMime() ).put( getURI() );
		
		// Then
		assertThat( response.getStatusCode(), is( 415 ) );
	}
	
	@Test
	public void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived(){
		// Given
		final T unpersistedEntity = getTemplate().createNewEntity();
		unpersistedEntity.setId( IDUtils.randomPositiveLong() );
		
		// When
		final Response response = getTemplate().updateAsResponse( unpersistedEntity );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted(){
		// Given
		final T existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		getTemplate().change( existingResource );
		getTemplate().update( existingResource );
		
		final T updatedResourceFromServer = getTemplate().findOne( existingResource.getId() );
		
		// Then
		assertEquals( existingResource, updatedResourceFromServer );
	}
	
	// delete
	
	@Test
	public void whenAResourceIsDeletedByIncorrectNonNumericId_then400IsReceived(){
		// When
		final Response response = getTemplate().deleteAsResponse( getURI() + randomAlphabetic( 6 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 400 ) );
	}
	
	@Test
	public void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived(){
		// When
		final Response response = getTemplate().deleteAsResponse( getURI() + randomNumeric( 6 ) );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	@Test
	public void givenResourceExist_whenResourceIsDeleted_then204IsReceived(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response response = getTemplate().deleteAsResponse( uriForResourceCreation );
		
		// Then
		assertThat( response.getStatusCode(), is( 204 ) );
	}
	
	@Test
	public void givenResourceExist_whenResourceIsDeleted_thenResourceIsNoLongerAvailable(){
		// Given
		final String uriOfResource = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		getTemplate().deleteAsResponse( uriOfResource );
		
		// When
		final Response getResponse = getTemplate().findOneAsResponse( uriOfResource );
		
		// Then
		assertThat( getResponse.getStatusCode(), is( 404 ) );
	}
	
	// template method
	
	protected abstract IRESTTemplate< T > getTemplate();
	
	protected abstract String getURI();
	
	protected abstract RequestSpecification givenAuthenticated();
	
}
