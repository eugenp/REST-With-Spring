package org.rest.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

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
		this.getService().findAll();
	}
	
	@Test
	public void whenEntitiesAreRetrieved_thenTheResultIsNotNull(){
		final List< T > entities = this.getService().findAll();
		
		assertNotNull( entities );
	}
	
	@Test
	public void givenAnEntityExists_whenEntitiesAreRetrieved_thenThereIsAtLeastOneEntity(){
		this.persistNewEntity();
		
		final List< T > owners = this.getService().findAll();
		
		assertThat( owners, Matchers.not( Matchers.<T> empty() ) );
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
		final T existingLocation = this.persistNewEntity();
		
		// When
		this.getService().delete( existingLocation.getId() );
	}
	
	@Test
	public void givenEntityExists_whenEntityIsDeleted_thenEntityIsNoLongerRetrievable(){
		// Given
		final T existingEntity = this.persistNewEntity();
		
		// When
		this.getService().delete( existingEntity.getId() );
		
		// Then
		assertNull( this.getService().findOne( existingEntity.getId() ) );
	}
	
	//
	
	protected abstract IService< T > getService();
	
	//
	
	protected abstract T createNewEntity();
	
	protected T persistNewEntity(){
		return this.getService().create( this.createNewEntity() );
	}
	
}
