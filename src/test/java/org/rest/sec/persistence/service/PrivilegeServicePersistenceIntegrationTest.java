package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class PrivilegeServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Privilege >{
	
	@Autowired
	private IPrivilegeService service;
	
	// create
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		service.save( createNewEntity() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		
		service.save( this.createNewEntity( name ) );
		service.save( this.createNewEntity( name ) );
	}
	
	// template method
	
	@Override
	protected final IPrivilegeService getService(){
		return service;
	}
	@Override
	protected final Privilege createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final Privilege createNewEntity( final String name ){
		return new Privilege( name );
	}
	
}
