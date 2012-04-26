package org.rest.persistence;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.IEntity;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingConfig;
import org.rest.util.IDUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public abstract class AbstractPersistenceDAOIntegrationTest< T extends IEntity >{
	
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
		getAPI().save( createNewEntity() );
		
		final List< T > owners = getAPI().findAll();
		
		assertThat( owners, Matchers.not( Matchers.<T> empty() ) );
	}
	
	@Test
	public void givenAnEntityExists_whenEntitiesAreRetrieved_thenTheExistingEntityIsIndeedAmongThem(){
		final T existingEntity = getAPI().save( createNewEntity() );
		
		final List< T > owners = getAPI().findAll();
		
		assertThat( owners, hasItem( existingEntity ) );
	}
	
	// find one
	
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenNoExceptions(){
		final T existingEntity = getAPI().save( createNewEntity() );
		getAPI().findOne( existingEntity.getId() );
	}
	
	@Test
	public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenNoExceptions(){
		getAPI().findOne( IDUtils.randomPositiveLong() );
	}
	
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenTheResultIsNotNull(){
		final T existingEntity = getAPI().save( createNewEntity() );
		final T retrievedEntity = getAPI().findOne( existingEntity.getId() );
		assertNotNull( retrievedEntity );
	}
	
	@Test
	public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenTheResultIsNull(){
		final T retrievedEntity = getAPI().findOne( IDUtils.randomPositiveLong() );
		assertNull( retrievedEntity );
	}
	
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenEntityIsRetrievedCorrectly(){
		final T existingEntity = getAPI().save( createNewEntity() );
		final T retrievedEntity = getAPI().findOne( existingEntity.getId() );
		assertEquals( existingEntity, retrievedEntity );
	}
	
	// save/create
	
	@Test( expected = RuntimeException.class )
	public void whenNullEntityIsCreated_thenException(){
		getAPI().save( (T) null );
	}
	
	@Test
	public void whenEntityIsCreated_thenNoExceptions(){
		getAPI().save( createNewEntity() );
	}
	
	@Test
	public void whenEntityIsCreated_thenEntityIsRetrievable(){
		final T existingEntity = getAPI().save( createNewEntity() );
		
		assertNotNull( getAPI().findOne( existingEntity.getId() ) );
	}
	
	@Test
	public void whenEntityIsSaved_thenSavedEntityIsEqualToOriginalEntity(){
		final T originalEntity = createNewEntity();
		final T savedEntity = getAPI().save( originalEntity );
		
		assertEquals( originalEntity, savedEntity );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenEntityWithFailedConstraintsIsCreated_thenException(){
		final T invalidEntity = createNewEntity();
		invalidate( invalidEntity );
		
		getAPI().save( invalidEntity );
	}
	
	// update
	
	@Test
	public void whenEntityIsUpdated_thenNoExceptions(){
		final T existingEntity = getAPI().save( createNewEntity() );
		
		getAPI().save( existingEntity );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenEntityIsUpdatedWithFailedConstraints_thenException(){
		final T existingEntity = getAPI().save( createNewEntity() );
		invalidate( existingEntity );
		
		getAPI().save( existingEntity );
	}
	
	@Test
	public void whenEntityIsUpdated_thenTheUpdatedAreCorrectlyPersisted(){
		final T existingEntity = getAPI().save( createNewEntity() );
		changeEntity( existingEntity );
		
		getAPI().save( existingEntity );
		
		final T updatedEntity = getAPI().findOne( existingEntity.getId() );
		assertEquals( existingEntity, updatedEntity );
	}
	
	// delete
	
	@Test( expected = DataAccessException.class )
	public void givenEntityDoesNotExists_whenEntityIsDeleted_thenDataAccessException(){
		// When
		getAPI().delete( IDUtils.randomPositiveLong() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenEntityIsDeletedByNegativeId_thenDataAccessException(){
		// When
		getAPI().delete( ( IDUtils.randomNegativeLong() ) );
	}
	
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenNoExceptions(){
		// Given
		final T existingLocation = getAPI().save( createNewEntity() );
		
		// When
		getAPI().delete( existingLocation.getId() );
	}
	
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenEntityIsNoLongerRetrievable(){
		// Given
		final T existingEntity = getAPI().save( createNewEntity() );
		
		// When
		getAPI().delete( existingEntity.getId() );
		
		// Then
		assertNull( getAPI().findOne( existingEntity.getId() ) );
	}
	
	// delete all
	// - note: the goal of these tests is to be independent of each other; because of this, deleteAll is not an option
	
	// template method
	
	protected abstract JpaRepository< T, Long > getAPI();
	
	protected abstract T createNewEntity();
	
	protected abstract void invalidate( final T entity );
	
	protected abstract void changeEntity( final T entity );
	
}
