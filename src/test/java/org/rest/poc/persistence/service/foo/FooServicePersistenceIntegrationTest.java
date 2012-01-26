package org.rest.poc.persistence.service.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.poc.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class FooServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Foo >{
	
	@Autowired
	IFooService service;
	
	// create
	
	@Test
	public final void whenSaveIsPerformed_thenNoException(){
		service.save( new Foo( randomAlphabetic( 8 ) ) );
	}
	
	@Test( expected = DataAccessException.class )
	public final void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		service.save( new Foo( name ) );
		service.save( new Foo( name ) );
	}
	
	// template method
	
	@Override
	protected IFooService getService(){
		return service;
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
