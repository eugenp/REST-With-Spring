package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.common.collect.Sets;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
@SuppressWarnings( "unchecked" )
@TransactionConfiguration( defaultRollback = true )
public class RoleServiceSearchPersistenceIntegrationTest{
	
	@Autowired private IPrivilegeService privilegeService;
	@Autowired private IRoleService roleService;
	@Autowired private IPrincipalService principalService;
	
	// fixtures
	
	/** - note: temporary, until: https://github.com/eugenp/REST/issues/7 */
	@Before
	public final void before(){
		principalService.deleteAll();
		roleService.deleteAll();
		privilegeService.deleteAll();
	}
	
	// search/filter
	
	@Test
	public final void whenSearchByNameIsPerformed_thenNoExceptions(){
		final Role existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( "name", existingEntity.getName() );
		getService().search( nameConstraint );
	}
	
	@Test
	public final void givenEntityExists_whenSearchByNameIsPerformed_thenResultIsFound(){
		final Role existingEntity = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( "name", existingEntity.getName() );
		final List< Role > searchResults = getService().search( nameConstraint );
		
		// Then
		assertThat( searchResults, hasItem( existingEntity ) );
	}
	@Test
	public final void givenEntityExists_whenSearchByIdNegatedNameIsPerformed_thenResultsAreFound(){
		final Role existingEntity1 = getService().create( createNewEntity() );
		final Role existingEntity2 = getService().create( createNewEntity() );
		
		// When
		final ImmutablePair< String, String > nameConstraint = new ImmutablePair< String, String >( "name", existingEntity1.getName() );
		final List< Role > searchResults = getService().search( nameConstraint );
		
		// Then
		assertThat( searchResults, hasItem( existingEntity1 ) );
		assertThat( searchResults, not( hasItem( existingEntity2 ) ) );
	}
	
	// template method
	
	protected final IRoleService getService(){
		return roleService;
	}
	protected final Role createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	protected final void invalidate( final Role entity ){
		entity.setName( null );
	}
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
