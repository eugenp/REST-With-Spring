package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.User;
import org.rest.sec.persistence.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class UserServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< User >{
	
	@Autowired
	private IUserService service;
	
	// create
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		service.create( createNewEntity() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		
		service.create( this.createNewEntity( name ) );
		service.create( this.createNewEntity( name ) );
	}
	
	// template method
	
	@Override
	protected final IUserService getService(){
		return service;
	}
	@Override
	protected final User createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final User createNewEntity( final String username ){
		return new User( username, randomAlphabetic( 8 ) );
	}
	
}
