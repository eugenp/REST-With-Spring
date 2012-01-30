package org.rest.poc.persistence.service.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.poc.model.User;
import org.rest.poc.persistence.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class UserServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< User >{
	
	@Autowired
	IUserService service;
	
	// create
	
	@Test
	public final void whenSaveIsPerformed_thenNoException(){
		service.save( new User( randomAlphabetic( 8 ) ) );
	}
	
	@Test( expected = DataAccessException.class )
	public final void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		service.save( new User( name ) );
		service.save( new User( name ) );
	}
	
	// template method
	
	@Override
	protected IUserService getService(){
		return service;
	}
	
	@Override
	protected final User createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final User createNewEntity( final String name ){
		return new User( name );
	}
	
}
