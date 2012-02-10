package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class RoleServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Role >{
	
	@Autowired
	private IRoleService service;
	
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
	protected final IRoleService getService(){
		return service;
	}
	@Override
	protected final Role createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final Role createNewEntity( final String name ){
		return new Role( name );
	}
	
}
