package org.rest.service.hello.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.rest.dao.hello.impl.HelloDAO;
import org.rest.model.Hello;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HelloServiceUnitTest{
	
	HelloService instance;
	
	private HibernateTemplate hibernateTemplateMock;
	
	@Before
	public final void before(){
		this.instance = new HelloService();
		this.instance.dao = new HelloDAO();
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
		this.instance.create( new Hello( "testName" ) );
		
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
		this.instance.create( new Hello( "testName" ) );
		
		// Then
		verify( this.hibernateTemplateMock ).save( any( Hello.class ) );
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
		verify( this.hibernateTemplateMock ).get( Hello.class, 1l );
	}
	
	// update
	
	@Test
	public final void whenUpdateIsTriggered_thenNoException(){
		// When
		this.instance.update( new Hello( "testName" ) );
		
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
		final Hello entity = new Hello( "testName" );
		this.instance.update( entity );
		
		// Then
		verify( this.hibernateTemplateMock ).saveOrUpdate( entity );
	}
	
	// delete
	
	@Test
	public final void whenDeleteIsTriggered_thenNoException(){
		// When
		this.instance.delete( new Hello( "testName" ) );
		
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
		final Hello entity = new Hello( "testName" );
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
		verify( this.hibernateTemplateMock ).loadAll( Hello.class );
	}
	
}
