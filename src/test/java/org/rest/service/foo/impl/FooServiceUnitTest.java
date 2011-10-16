package org.rest.service.foo.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.rest.dao.foo.impl.FooDAO;
import org.rest.model.Foo;
import org.rest.service.foo.impl.FooService;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class FooServiceUnitTest{
	
	FooService instance;
	
	private HibernateTemplate hibernateTemplateMock;
	
	@Before
	public final void before(){
		this.instance = new FooService();
		this.instance.dao = new FooDAO();
		this.hibernateTemplateMock = mock( HibernateTemplate.class );
		this.instance.dao.setHibernateTemplate( this.hibernateTemplateMock );
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
		verify( this.hibernateTemplateMock ).save( any( Foo.class ) );
	}
	
	// get
	
	@Test
	public final void whenGetIsTriggered_thenNoException(){
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
		// When
		this.instance.getById( 1l );
		
		// Then
		verify( this.hibernateTemplateMock ).get( Foo.class, 1l );
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
		verify( this.hibernateTemplateMock ).saveOrUpdate( entity );
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
		verify( this.hibernateTemplateMock ).delete( entity );
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
		verify( this.hibernateTemplateMock ).loadAll( Foo.class );
	}
	
}
