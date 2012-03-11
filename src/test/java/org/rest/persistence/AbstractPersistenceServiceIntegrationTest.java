package org.rest.persistence;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.persistence.service.IService;
import org.rest.util.IdUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;

public abstract class AbstractPersistenceServiceIntegrationTest< T extends IEntity >{
	
	// tests
	
	// find - findAll
	
	@Test
	public void whenEntitiesAreRetrieved_thenNoExceptions(){
		getAPI().findAll();
	}
	@Test
	public void whenEntitiesAreRetrieved_thenTheResultIsNotNull(){
		final List< T > entities = getAPI().findAll();
		
		assertNotNull( entities );
	}
	@Test
	public void givenAnEntityExists_whenEntitiesAreRetrieved_thenThereIsAtLeastOneEntity(){
		this.getAPI().create( this.createNewEntity() );
		
		final List< T > owners = getAPI().findAll();
		
		assertThat( owners, Matchers.not( Matchers.<T> empty() ) );
	}
	@Test
	public void givenAnEntityExists_whenEntitiesAreRetrieved_thenTheExistingEntityIsIndeedAmongThem(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		
		final List< T > owners = getAPI().findAll();
		
		assertThat( owners, hasItem( existingEntity ) );
	}
	
	// find one
	
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenNoExceptions(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		getAPI().findOne( existingEntity.getId() );
	}
	@Test
	public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenNoExceptions(){
		getAPI().findOne( IdUtil.randomPositiveLong() );
	}
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenTheResultIsNotNull(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		final T retrievedEntity = getAPI().findOne( existingEntity.getId() );
		assertNotNull( retrievedEntity );
	}
	@Test
	public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenTheResultIsNull(){
		final T retrievedEntity = getAPI().findOne( IdUtil.randomPositiveLong() );
		assertNull( retrievedEntity );
	}
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenEntityIsRetrievedCorrectly(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		final T retrievedEntity = getAPI().findOne( existingEntity.getId() );
		assertEquals( existingEntity, retrievedEntity );
	}
	
	// save/create
	
	@Test( expected = RuntimeException.class )
	public void whenNullEntityIsCreated_thenException(){
		getAPI().create( null );
	}
	@Test
	@Rollback
	public void whenEntityIsCreated_thenNoExceptions(){
		this.getAPI().create( this.createNewEntity() );
	}
	@Test
	public void whenEntityIsCreated_thenEntityIsRetrievable(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		
		assertNotNull( getAPI().findOne( existingEntity.getId() ) );
	}
	@Test
	public void whenEntityIsCreated_thenSavedEntityIsEqualToOriginalEntity(){
		final T originalEntity = createNewEntity();
		final T savedEntity = getAPI().create( originalEntity );
		
		assertEquals( originalEntity, savedEntity );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenEntityWithFailedConstraintsIsCreated_thenException(){
		final T invalidEntity = createNewEntity();
		invalidate( invalidEntity );
		
		getAPI().create( invalidEntity );
	}
	
	// -- specific to the persistence engine
	
	@Test( expected = DataAccessException.class )
	@Ignore( "Hibernate simply ignores the id silently and still saved (tracking this)" )
	public void whenEntityWithIdIsCreated_thenDataAccessException(){
		final T entityWithId = createNewEntity();
		entityWithId.setId( IdUtil.randomPositiveLong() );
		
		getAPI().create( entityWithId );
	}
	
	// update
	
	@Test( expected = RuntimeException.class )
	public void whenNullEntityIsUpdated_thenException(){
		getAPI().update( null );
	}
	@Test
	public void whenEntityIsUpdated_thenNoExceptions(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		
		getAPI().update( existingEntity );
	}
	@Test( expected = DataAccessException.class )
	public void whenEntityIsUpdatedWithFailedConstraints_thenException(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		invalidate( existingEntity );
		
		getAPI().update( existingEntity );
	}
	@Test
	public void whenEntityIsUpdated_thenTheUpdatedAreCorrectlyPersisted(){
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		changeEntity( existingEntity );
		
		getAPI().update( existingEntity );
		
		final T updatedEntity = getAPI().findOne( existingEntity.getId() );
		assertEquals( existingEntity, updatedEntity );
	}
	
	// delete
	
	@Test( expected = DataAccessException.class )
	public void givenEntityDoesNotExists_whenEntityIsDeleted_thenDataAccessException(){
		// When
		getAPI().delete( IdUtil.randomPositiveLong() );
	}
	@Test( expected = DataAccessException.class )
	public void whenEntityIsDeletedByNegativeId_thenDataAccessException(){
		// When
		getAPI().delete( ( IdUtil.randomNegativeLong() ) );
	}
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenNoExceptions(){
		// Given
		final T existingLocation = this.getAPI().create( this.createNewEntity() );
		
		// When
		getAPI().delete( existingLocation.getId() );
	}
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenEntityIsNoLongerRetrievable(){
		// Given
		final T existingEntity = this.getAPI().create( this.createNewEntity() );
		
		// When
		getAPI().delete( existingEntity.getId() );
		
		// Then
		assertNull( getAPI().findOne( existingEntity.getId() ) );
	}
	
	// delete all
	// - note: the goal of these tests is to be independent of each other; because of this, deleteAll is not an option
	
	// template method
	
	protected abstract IService< T > getAPI();
	
	protected abstract T createNewEntity();
	
	protected abstract void invalidate( final T entity );
	protected abstract void changeEntity( final T entity );
	
}
