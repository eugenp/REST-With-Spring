package org.rest.client;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.rest.client.template.IEntityOperations;
import org.rest.common.IEntity;
import org.rest.util.IDUtils;

public abstract class AbstractClientRESTIntegrationTest< T extends IEntity >{
	
	public AbstractClientRESTIntegrationTest(){
		super();
	}
	
	// tests
	
	// find one
	
	@Test
	public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved(){
		// Given
		final T newResource = getEntityOps().createNewEntity();
		final String uriOfExistingResource = getTemplate().createAsURI( newResource );
		
		// When
		final T createdResource = getTemplate().findOneByURI( uriOfExistingResource );
		
		// Then
		assertEquals( createdResource, newResource );
	}
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived(){
		// When
		final T createdResource = getTemplate().findOne( IDUtils.randomPositiveLong() );
		
		// Then
		assertNull( createdResource );
	}
	
	// find all
	
	@Test
	public final void whenAllResourcesAreRetrieved_thenResourcesExist(){
		final List< T > allResources = getTemplate().findAll();
		assertFalse( allResources.isEmpty() );
	}
	
	// update
	
	@Test
	public final void whenResourceIsUpdated_thenTheChangesAreCorrectlyPersisted(){
		final T existingResource = getTemplate().givenAuthenticated().create( getEntityOps().createNewEntity() );
		
		// When
		getEntityOps().change( existingResource );
		getTemplate().givenAuthenticated().update( existingResource );
		final T updatedResource = getTemplate().findOne( existingResource.getId() );
		
		// Then
		assertThat( existingResource, equalTo( updatedResource ) );
	}
	
	// delete
	
	@Test
	public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists(){
		// Given
		final T existingResource = getTemplate().givenAuthenticated().create( getEntityOps().createNewEntity() );
		
		// When
		getTemplate().delete( existingResource.getId() );
		
		// Then
		assertNull( getTemplate().findOne( existingResource.getId() ) );
	}
	
	// template method
	
	protected abstract AbstractClientRESTTemplate< T > getTemplate();
	
	protected abstract IEntityOperations< T > getEntityOps();
	
}
