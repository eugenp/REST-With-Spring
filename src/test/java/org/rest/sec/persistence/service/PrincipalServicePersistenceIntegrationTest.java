package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.google.common.collect.Sets;

public class PrincipalServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Principal >{
	
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
		principalService.create( createNewEntity() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		
		principalService.create( createNewEntity( name ) );
		principalService.create( createNewEntity( name ) );
	}
	
	// Spring
	
	@Override
	protected final IPrincipalService getService(){
		return principalService;
	}

	// template method
	
	@Override
	protected final Principal createNewEntity(){
		return new Principal( randomAlphabetic( 8 ), randomAlphabetic( 8 ), Sets.<Role> newHashSet() );
	}
	
	protected final Principal createNewEntity( final String name ){
		return new Principal( name, randomAlphabetic( 8 ), Sets.<Role> newHashSet() );
	}

	@Override
	protected final void invalidateEntity( final Principal entity ){
		entity.setName( null );
	}
	
	@Override
	protected void changeEntity( final Principal entity ){
		entity.setName( randomAlphabetic( 8 ) );
	}
	
}
