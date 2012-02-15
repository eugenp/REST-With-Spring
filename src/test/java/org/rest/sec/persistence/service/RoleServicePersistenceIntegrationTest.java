package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

public class RoleServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Role >{
	
	@Autowired
	private IPrivilegeService privilegeService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPrincipalService principalService;
	
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
		roleService.create( createNewEntity() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		
		roleService.create( this.createNewEntity( name ) );
		roleService.create( this.createNewEntity( name ) );
	}
	
	// template method
	
	@Override
	protected final IRoleService getService(){
		return roleService;
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
