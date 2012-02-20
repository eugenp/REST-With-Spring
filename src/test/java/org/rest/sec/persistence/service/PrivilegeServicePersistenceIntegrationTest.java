package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class PrivilegeServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Privilege >{
	
	@Autowired private IPrivilegeService privilegeService;
	@Autowired private IRoleService roleService;
	@Autowired private IPrincipalService principalService;
	
	// fixtures
	
	/**
	 * - note: temporary, until: https://github.com/eugenp/REST/issues/7
	 */
	@Before
	public final void before(){
		privilegeService.deleteAll();
		roleService.deleteAll();
		principalService.deleteAll();
	}
	
	// create
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		privilegeService.create( createNewEntity() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		
		privilegeService.create( this.createNewEntity( name ) );
		privilegeService.create( this.createNewEntity( name ) );
	}
	
	// template method
	
	@Override
	protected final IPrivilegeService getService(){
		return privilegeService;
	}
	@Override
	protected final Privilege createNewEntity(){
		return new Privilege( randomAlphabetic( 8 ) );
	}
	@Override
	protected final void invalidateEntity( final Privilege entity ){
		entity.setName( null );
	}
	@Override
	protected final void changeEntity( final Privilege entity ){
		entity.setName( randomAlphabetic( 6 ) );
	}
	
	// util
	
	protected final Privilege createNewEntity( final String name ){
		return new Privilege( name );
	}
	
}
