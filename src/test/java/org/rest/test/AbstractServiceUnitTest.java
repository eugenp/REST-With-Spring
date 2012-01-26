package org.rest.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.persistence.service.IService;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Preconditions;

public abstract class AbstractServiceUnitTest< T extends IEntity >{
	
	private Class< T > clazz;
	
	public AbstractServiceUnitTest( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	//
	
	@Test
	public final void whenServiceIsInitialized_thenNoException(){
		// When
		// Then
	}
	
	// create
	
	@Test
	public final void whenCreateIsTriggered_thenNoException(){
		// When
		getService().save( this.createNewEntity() );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenCreateIsTriggeredForNullEntity_thenException(){
		// When
		getService().save( null );
		
		// Then
	}
	
	@Test
	public final void whenCreateIsTriggered_thenEntityIsCreated(){
		// When
		getService().save( this.createNewEntity() );
		
		// Then
		verify( getDAOMock() ).save( any( clazz ) );
	}
	
	// get
	/*
	@Test
	public final void whenGetIsTriggered_thenNoException(){
		this.configureGet( 1l );
		
		// When
		getService().findOne( 1l );
		
		// Then
	}
	 */
	@Test( expected = NullPointerException.class )
	public final void whenGetIsTriggeredForNullId_thenException(){
		// When
		getService().save( null );
		
		// Then
	}
	/*
	@Test
	public final void whenGetIsTriggered_thenEntityIsRetrieved(){
		this.configureGet( 1l );

		// When
		getService().findOne( 1l );
		
		// Then
		verify( getDAOMock() ).findOne( 1l );
	}
	 */
	// update
	
	@Test
	public final void whenUpdateIsTriggered_thenNoException(){
		// When
		getService().update( this.createNewEntity() );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenUpdateIsTriggeredForNullEntity_thenException(){
		// When
		getService().update( null );
		
		// Then
	}
	
	@Test
	public final void whenUpdateIsTriggered_thenEntityIsUpdated(){
		// When
		final T entity = this.createNewEntity();
		getService().update( entity );
		
		// Then
		verify( getDAOMock() ).save( entity );
	}
	
	// delete
	
	/**
	 * - note: the responsibility of ensuring data integrity belongs to the database; because this is an unit test, then no exception is thrown
	 */
	@Test
	public final void givenResourceDoesNotExist_whenDeleteIsTriggered_thenNothingHappens(){
		// When
		getService().delete( new Random().nextLong() );
		
		// Then
	}
	
	@Test
	public final void givenResourceExists_whenDeleteIsTriggered_thenNoExceptions(){
		// Given
		when( getDAOMock().findOne( 1l ) ).thenReturn( this.createNewEntity() );
		
		// When
		getService().delete( new Random().nextLong() );
		
		// Then
	}
	
	@Test
	public final void givenResourceExists_whenDeleteIsTriggered_thenEntityIsDeleted(){
		// Given
		when( getDAOMock().findOne( 1l ) ).thenReturn( this.createNewEntity() );
		
		// When
		getService().delete( 1l );
		
		// Then
		verify( getDAOMock() ).delete( 1l );
	}
	
	// getAll
	
	@Test
	public final void whenGetAllIsTriggered_thenNoException(){
		// When
		getService().findAll();
		
		// Then
	}
	
	@Test
	public final void whenGetAllIsTriggered_thenAllEntitiesAreRetrieved(){
		// When
		getService().findAll();
		
		// Then
		verify( getDAOMock() ).findAll();
	}
	
	// template method
	
	protected abstract IService< T > getService();
	protected abstract JpaRepository< T, Long > getDAOMock();
	protected abstract T createNewEntity();
}
