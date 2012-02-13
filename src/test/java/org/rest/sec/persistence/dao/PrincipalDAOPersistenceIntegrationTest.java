package org.rest.sec.persistence.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceDAOIntegrationTest;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@TransactionConfiguration( defaultRollback = true )
@Transactional
public class PrincipalDAOPersistenceIntegrationTest extends AbstractPersistenceDAOIntegrationTest< Principal >{
	
	@Autowired
	private IPrincipalJpaDAO dao;
	
	@Autowired
	private IRoleJpaDAO associationDao;
	
	// involving other entities
	
	@Test
	public void whenPrincipalIsCreated_thenRolesOfUserAreLoaded(){
		final Principal persistedPrincipal = this.persistNewEntity();
		
		assertThat( persistedPrincipal.getRoles(), notNullValue() );
	}
	@Test
	public void whenUserIsCreated_thenCorectPrivilegesAreLoaded(){
		final String nameOfRole = "testRole";
		
		final Principal principalWitoutRoles = this.persistNewEntity();
		final Role savedAssociation = associationDao.save( new Role( nameOfRole ) );
		principalWitoutRoles.getRoles().add( savedAssociation );
		final Principal principalWithPrivilege = getDAO().save( principalWitoutRoles );
		
		assertThat( principalWithPrivilege.getRoles(), contains( new Role( nameOfRole ) ) );
	}
	
	// save
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		dao.save( createNewEntity() );
	}
	
	// find by
	
	@Test
	public void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound(){
		// Given
		final String name = randomAlphabetic( 8 );
		
		// When
		final Principal entityByName = dao.findByName( name );
		
		// Then
		assertNull( entityByName );
	}
	
	// template method
	
	@Override
	protected final IPrincipalJpaDAO getDAO(){
		return dao;
	}
	
	@Override
	protected final Principal createNewEntity(){
		final Principal principal = new Principal( randomAlphabetic( 8 ), randomAlphabetic( 8 ), Sets.<Role> newHashSet() );
		return principal;
	}
	
}
