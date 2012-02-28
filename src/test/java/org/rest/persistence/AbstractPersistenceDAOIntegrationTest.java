package org.rest.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.IEntity;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingTestConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public abstract class AbstractPersistenceDAOIntegrationTest< T extends IEntity >{
	
	// tests
	
	// find - findAll
	
	@Test
	public void whenEntitiesAreRetrieved_thenNoExceptions(){
		getDAO().findAll();
	}
	@Test
	public void whenEntitiesAreRetrieved_thenTheResultIsNotNull(){
		final List< T > entities = getDAO().findAll();
		
		assertNotNull( entities );
	}
	@Test
	public void givenAnEntityExists_whenEntitiesAreRetrieved_thenThereIsAtLeastOneEntity(){
		persistNewEntity();
		
		final List< T > owners = getDAO().findAll();
		
		assertThat( owners, Matchers.not( Matchers.<T> empty() ) );
	}
	
	// find one
	
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenNoExceptions(){
		final T existingEntity = persistNewEntity();
		getDAO().findOne( existingEntity.getId() );
	}
	@Test
	public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenNoExceptions(){
		long id = new Random().nextLong() * 10000;
		id = ( id < 0 ) ? ( -1 * id ) : id;
		getDAO().findOne( id );
	}
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenTheResultIsNotNull(){
		final T existingEntity = persistNewEntity();
		final T retrievedEntity = getDAO().findOne( existingEntity.getId() );
		assertNotNull( retrievedEntity );
	}
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenEntityIsRetrievedCorrectly(){
		final T existingEntity = persistNewEntity();
		final T retrievedEntity = getDAO().findOne( existingEntity.getId() );
		assertEquals( existingEntity, retrievedEntity );
	}
	
	// save
	
	@Test
	public void whenEntityIsCreated_thenNoExceptions(){
		this.persistNewEntity();
	}
	@Test
	public void whenEntityIsCreated_thenEntityIsRetrievable(){
		final T existingEntity = this.persistNewEntity();
		
		assertNotNull( this.getDAO().findOne( existingEntity.getId() ) );
	}
	@Test
	public void whenEntityIsSaved_thenSavedEntityIsEqualToOriginalEntity(){
		final T originalEntity = this.createNewEntity();
		final T savedEntity = this.getDAO().save( originalEntity );
		
		assertEquals( originalEntity, savedEntity );
	}
	
	// delete
	
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenNoExceptions(){
		// Given
		final T existingLocation = persistNewEntity();
		
		// When
		getDAO().delete( existingLocation.getId() );
	}
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenEntityIsNoLongerRetrievable(){
		// Given
		final T existingEntity = persistNewEntity();
		
		// When
		getDAO().delete( existingEntity.getId() );
		
		// Then
		assertNull( getDAO().findOne( existingEntity.getId() ) );
	}
	
	// delete all
	
	@Test
	public void whenEntitiesAreDeleted_thenNoException(){
		getDAO().deleteAll();
	}
	@Test
	public void givenEntityExists_whenEntitiesAreDeleted_thenNoException(){
		// Given
		persistNewEntity();
		
		// When
		getDAO().deleteAll();
	}
	@Test
	public void givenNoEntityExists_whenEntitiesAreDeleted_thenNoException(){
		// Given
		getDAO().deleteAll();
		
		// When
		getDAO().deleteAll();
	}
	@Test
	public void givenEntityExists_whenEntitiesAreDeleted_thenEntitiesNoLongerRetrievable(){
		// Given
		persistNewEntity();
		
		// When
		getDAO().deleteAll();
		
		// Then
		assert ( getDAO().findAll().size() == 0 );
	}
	
	//
	
	protected abstract JpaRepository< T, Long > getDAO();
	
	//
	
	protected abstract T createNewEntity();
	
	protected T persistNewEntity(){
		return this.getDAO().save( this.createNewEntity() );
	}
	protected T persistNewEntity( final T entity ){
		return this.getDAO().save( entity );
	}
	
}
