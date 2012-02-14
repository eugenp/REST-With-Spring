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
import org.rest.persistence.service.IService;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@Transactional
public abstract class AbstractPersistenceServiceIntegrationTest< T extends IEntity >{

	// tests
	
	// find - findAll
	
	@Test
	public void whenEntitiesAreRetrieved_thenNoExceptions(){
		getService().findAll();
	}
	@Test
	public void whenEntitiesAreRetrieved_thenTheResultIsNotNull(){
		final List< T > entities = getService().findAll();
		
		assertNotNull( entities );
	}
	@Test
	public void givenAnEntityExists_whenEntitiesAreRetrieved_thenThereIsAtLeastOneEntity(){
		persistNewEntity();
		
		final List< T > owners = getService().findAll();
		
		assertThat( owners, Matchers.not( Matchers.<T> empty() ) );
	}
	
	// find one
	
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenNoExceptions(){
		final T existingEntity = persistNewEntity();
		getService().findOne( existingEntity.getId() );
	}
	@Test
	public void givenEntityDoesNotExist_whenEntityIsRetrieved_thenNoExceptions(){
		long id = new Random().nextLong() * 10000;
		id = ( id < 0 ) ? ( -1 * id ) : id;
		getService().findOne( id );
	}
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenTheResultIsNotNull(){
		final T existingEntity = persistNewEntity();
		final T retrievedEntity = getService().findOne( existingEntity.getId() );
		assertNotNull( retrievedEntity );
	}
	@Test
	public void givenEntityExists_whenEntityIsRetrieved_thenEntityIsRetrievedCorrectly(){
		final T existingEntity = persistNewEntity();
		final T retrievedEntity = getService().findOne( existingEntity.getId() );
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
		
		assertNotNull( this.getService().findOne( existingEntity.getId() ) );
	}
	@Test
	public void whenEntityIsSaved_thenSavedEntityIsEqualToOriginalEntity(){
		final T originalEntity = this.createNewEntity();
		final T savedEntity = this.getService().create( originalEntity );
		
		assertEquals( originalEntity, savedEntity );
	}
	
	// delete
	
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenNoExceptions(){
		// Given
		final T existingLocation = persistNewEntity();
		
		// When
		getService().delete( existingLocation.getId() );
	}
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenEntityIsNoLongerRetrievable(){
		// Given
		final T existingEntity = persistNewEntity();
		
		// When
		getService().delete( existingEntity.getId() );
		
		// Then
		assertNull( getService().findOne( existingEntity.getId() ) );
	}
	
	// delete all
	
	@Test
	public void whenEntitiesAreDeleted_thenNoException(){
		getService().deleteAll();
	}
	@Test
	public void givenEntityExists_whenEntitiesAreDeleted_thenNoException(){
		// Given
		persistNewEntity();
		
		// When
		getService().deleteAll();
	}
	@Test
	public void givenNoEntityExists_whenEntitiesAreDeleted_thenNoException(){
		// Given
		getService().deleteAll();
		
		// When
		getService().deleteAll();
	}
	@Test
	public void givenEntityExists_whenEntitiesAreDeleted_thenEntitiesNoLongerRetrievable(){
		// Given
		persistNewEntity();
		
		// When
		getService().deleteAll();
		
		// Then
		assert ( getService().findAll().size() == 0 );
	}
	
	//
	
	protected abstract IService< T > getService();
	
	//
	
	protected abstract T createNewEntity();
	
	protected T persistNewEntity(){
		return this.getService().create( this.createNewEntity() );
	}
	
}
