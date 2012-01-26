package org.rest.poc.persistence.service.foo.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.rest.poc.model.Foo;
import org.rest.poc.persistence.dao.foo.IFooJpaDAO;
import org.rest.test.AbstractServiceUnitTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class FooServiceUnitTest extends AbstractServiceUnitTest< Foo >{
	
	FooServiceImpl instance;
	
	private IFooJpaDAO daoMock;
	
	public FooServiceUnitTest(){
		super( Foo.class );
	}
	
	// fixtures
	
	@Before
	public final void before(){
		instance = new FooServiceImpl();
		
		daoMock = mock( IFooJpaDAO.class );
		when( daoMock.findAll() ).thenReturn( Lists.<Foo> newArrayList() );
		instance.dao = daoMock;
		
		instance.setEventPublisher( mock( ApplicationEventPublisher.class ) );
	}
	
	// get
	
	@Test
	public final void whenGetIsTriggered_thenNoException(){
		configureGet( 1l );
		
		// When
		instance.findOne( 1l );
		
		// Then
	}
	
	@Test
	public final void whenGetIsTriggered_thenEntityIsRetrieved(){
		configureGet( 1l );
		// When
		instance.findOne( 1l );
		
		// Then
		verify( daoMock ).findOne( 1l );
	}
	
	// mocking behavior
	
	final Foo configureGet( final long id ){
		final Foo entity = new Foo();
		entity.setId( id );
		when( daoMock.findOne( id ) ).thenReturn( entity );
		return entity;
	}
	
	// template method
	
	@Override
	protected final FooServiceImpl getService(){
		return instance;
	}
	@Override
	protected final JpaRepository< Foo, Long > getDAOMock(){
		return daoMock;
	}
	@Override
	protected final Foo createNewEntity(){
		return new Foo( randomAlphabetic( 6 ) );
	}
	
}
