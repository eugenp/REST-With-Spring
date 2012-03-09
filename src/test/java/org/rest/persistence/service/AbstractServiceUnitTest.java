package org.rest.persistence.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.util.IdUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Preconditions;

public abstract class AbstractServiceUnitTest< T extends IEntity >{
	
	private Class< T > clazz;
	
	public AbstractServiceUnitTest( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// fixtures
	
	public void before(){
		final AbstractService< T > instance = (AbstractService< T >) getService();
		instance.eventPublisher = mock( ApplicationEventPublisher.class );
	}
	
	// tests
	
	@Test
	public final void whenServiceIsInitialized_thenNoException(){
		// When
		// Then
	}
	
	// create
	
	@Test
	public final void whenCreateIsTriggered_thenNoException(){
		// When
		getService().create( this.createNewEntity() );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenCreateIsTriggeredForNullEntity_thenException(){
		// When
		getService().create( null );
		
		// Then
	}
	
	@Test
	public final void whenCreateIsTriggered_thenEntityIsCreated(){
		// When
		getService().create( this.createNewEntity() );
		
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
		getService().create( null );
		
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
		getService().delete( IdUtil.randomPositiveLong() );
		
		// Then
	}
	
	@Test
	public final void givenResourceExists_whenDeleteIsTriggered_thenNoExceptions(){
		final long id = IdUtil.randomPositiveLong();
		
		// Given
		when( getDAOMock().findOne( id ) ).thenReturn( this.createNewEntity() );
		
		// When
		getService().delete( id );
		
		// Then
	}
	
	@Test
	public final void givenResourceExists_whenDeleteIsTriggered_thenEntityIsDeleted(){
		final long id = IdUtil.randomPositiveLong();
		
		// Given
		when( getDAOMock().findOne( id ) ).thenReturn( this.createNewEntity() );
		
		// When
		getService().delete( id );
		
		// Then
		verify( getDAOMock() ).delete( id );
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
