package org.rest.poc.persistence.dao.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceDaoIntegrationTest;
import org.rest.poc.model.Foo;
import org.rest.poc.persistence.dao.foo.IFooJpaDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class FooDAOPersistenceIntegrationTest extends AbstractPersistenceDaoIntegrationTest< Foo >{
	
	@Autowired
	IFooJpaDAO dao;
	
	/*@Before
	public final void before(){
		this.dao.deleteAll();
	}*/

	// save
	
	@Test
	public final void whenSaveIsPerformed_thenNoException(){
		dao.save( new Foo( randomAlphabetic( 8 ) ) );
	}
	
	// find by
	
	@Test
	public final void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound(){
		// Given
		final String name = randomAlphabetic( 8 );
		
		// When
		final Foo entityByName = dao.findByName( name );
		
		// Then
		assertNull( entityByName );
	}
	
	// template method
	
	@Override
	protected IFooJpaDAO getDAO(){
		return dao;
	}
	
	@Override
	protected final Foo createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final Foo createNewEntity( final String name ){
		return new Foo( name );
	}
}
