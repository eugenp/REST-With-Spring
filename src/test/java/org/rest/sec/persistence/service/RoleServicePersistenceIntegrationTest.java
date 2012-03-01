package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.google.common.collect.Sets;

public class RoleServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Role >{
	
	@Autowired private IPrivilegeService privilegeService;
	@Autowired private IRoleService roleService;
	@Autowired private IPrincipalService principalService;
	
	// fixtures
	
	/** - note: temporary, until: https://github.com/eugenp/REST/issues/7 */
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
	
	// scenario
	
	/** - known issue: this fails on a H2 database */
	@Test
	public final void givenEntityExistsWithAssociationScenarios_whenDeletingEverything_thenNoException(){
		final Privilege existingAssociation = getAssociationService().create( new Privilege( randomAlphabetic( 6 ) ) );
		final Role newResource = createNewEntity();
		newResource.getPrivileges().add( existingAssociation );
		getService().create( newResource );
		
		privilegeService.deleteAll();
		// roleService.deleteAll();
		// privilegeService.deleteAll();
	}
	
	@Test
	public final void whenCreatingNewResourceWithExistingAssociations_thenNewResourceIsCorrectlyCreated(){
		final Privilege existingAssociation = getAssociationService().create( new Privilege( randomAlphabetic( 6 ) ) );
		final Role newResource = createNewEntity();
		newResource.getPrivileges().add( existingAssociation );
		getService().create( newResource );
		
		final Role newResource2 = createNewEntity();
		newResource2.getPrivileges().add( existingAssociation );
		getService().create( newResource2 );
	}
	
	@Test
	public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted(){
		final Privilege existingAssociation = getAssociationService().create( new Privilege( randomAlphabetic( 6 ) ) );
		final Role resource1 = new Role( randomAlphabetic( 6 ), Sets.newHashSet( existingAssociation ) );
		
		final Role resource1ViewOfServerBefore = getService().create( resource1 );
		assertThat( resource1ViewOfServerBefore.getPrivileges(), hasItem( existingAssociation ) );
		
		final Role resource2 = new Role( randomAlphabetic( 6 ), Sets.newHashSet( existingAssociation ) );
		getService().create( resource2 );
		
		final Role resource1ViewOfServerAfter = getService().findOne( resource1ViewOfServerBefore.getId() );
		assertThat( resource1ViewOfServerAfter.getPrivileges(), hasItem( existingAssociation ) );
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
	@Override
	protected final void invalidate( final Role entity ){
		entity.setName( null );
	}
	@Override
	protected final void changeEntity( final Role entity ){
		entity.setName( randomAlphabetic( 6 ) );
	}
	
	// util
	
	protected final Role createNewEntity( final String name ){
		return new Role( name, Sets.<Privilege> newHashSet() );
	}
	
	final IPrivilegeService getAssociationService(){
		return privilegeService;
	}
	
}
