package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class UserServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Principal >{
	
	@Autowired
	private IPrincipalService service;
	
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
	protected final IPrincipalService getService(){
		return service;
	}
	@Override
	protected final Principal createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final Principal createNewEntity( final String username ){
		return new Principal( username, randomAlphabetic( 8 ) );
	}
	
}
