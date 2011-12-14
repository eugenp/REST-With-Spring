package org.rest.service.foo.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.stubbing.defaultanswers.ReturnsMocks;
import org.rest.common.dao.hibernate.AbstractHibernateDAO;
import org.rest.common.dao.hibernate.DAOTestHelper;
import org.rest.common.dao.hibernate.GenericHibernateDAO;
import org.rest.model.Foo;

public class FooServiceUnitTest{
	
	FooService instance;
	
	private Session sessionMock;
	
	@SuppressWarnings( "unchecked" )
	@Before
	public final void before(){
		this.instance = new FooService();
		
		final SessionFactory sessionFactoryMock = mock( SessionFactory.class );
		this.instance.setDao( new GenericHibernateDAO< Foo >() );
		this.instance.dao.setClazz( Foo.class );
		DAOTestHelper.initialize( (AbstractHibernateDAO< Foo >) this.instance.dao, sessionFactoryMock );
		this.sessionMock = mock( Session.class, new ReturnsMocks() );
		doReturn( this.sessionMock ).when( sessionFactoryMock ).getCurrentSession();
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
		this.instance.create( new Foo( "testName" ) );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenCreateIsTriggeredForNullEntity_thenException(){
		// When
		this.instance.create( null );
		
		// Then
	}
	
	@Test
	public final void whenCreateIsTriggered_thenEntityIsCreated(){
		// When
		this.instance.create( new Foo( "testName" ) );
		
		// Then
		verify( this.sessionMock ).persist( any( Foo.class ) );
	}
	
	// get
	
	@Test
	public final void whenGetIsTriggered_thenNoException(){
		this.configureGet( 1l );
		
		// When
		this.instance.getById( 1l );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenGetIsTriggeredForNullId_thenException(){
		// When
		this.instance.create( null );
		
		// Then
	}
	
	@Test
	public final void whenGetIsTriggered_thenEntityIsRetrieved(){
		this.configureGet( 1l );
		// When
		this.instance.getById( 1l );
		
		// Then
		verify( this.sessionMock ).get( Foo.class, 1l );
	}
	
	// update
	
	@Test
	public final void whenUpdateIsTriggered_thenNoException(){
		// When
		this.instance.update( new Foo( "testName" ) );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenUpdateIsTriggeredForNullEntity_thenException(){
		// When
		this.instance.update( null );
		
		// Then
	}
	
	@Test
	public final void whenUpdateIsTriggered_thenEntityIsUpdated(){
		// When
		final Foo entity = new Foo( "testName" );
		this.instance.update( entity );
		
		// Then
		verify( this.sessionMock ).merge( entity );
	}
	
	// delete
	
	@Test
	public final void whenDeleteIsTriggered_thenNoException(){
		// When
		this.instance.delete( new Foo( "testName" ) );
		
		// Then
	}
	
	@Test( expected = NullPointerException.class )
	public final void whenDeleteIsTriggeredForNullEntity_thenException(){
		// When
		this.instance.delete( null );
		
		// Then
	}
	
	@Test
	public final void whenDeleteIsTriggered_thenEntityIsDeleted(){
		// When
		final Foo entity = new Foo( "testName" );
		this.instance.delete( entity );
		
		// Then
		verify( this.sessionMock ).delete( entity );
	}
	
	// getAll
	
	@Test
	public final void whenGetAllIsTriggered_thenNoException(){
		// When
		this.instance.getAll();
		
		// Then
	}
	
	@Test
	public final void whenGetAllIsTriggered_thenAllEntitiesAreRetrieved(){
		// When
		this.instance.getAll();
		
		// Then
		verify( this.sessionMock ).createQuery( anyString() );
	}
	
	// mocking behavior
	
	final Foo configureGet( final long id ){
		final Foo entity = new Foo();
		entity.setId( id );
		when( this.sessionMock.get( Foo.class, id ) ).thenReturn( entity );
		return entity;
	}
	
}
