package org.rest.sec.persistence.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.AbstractPersistenceDAOIntegrationTest;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@TransactionConfiguration( defaultRollback = true )
@Transactional
public class RoleDAOPersistenceIntegrationTest extends AbstractPersistenceDAOIntegrationTest< Role >{
	
	@Autowired private IPrivilegeJpaDAO privilegeDao;
	@Autowired private IRoleJpaDAO roleDao;
	@Autowired private IPrincipalJpaDAO principalDao;
	
	// fixtures
	
	/**
	 * - note: temporary, until: https://github.com/eugenp/REST/issues/7
	 */
	@Before
	public final void before(){
		privilegeDao.deleteAll();
		roleDao.deleteAll();
		principalDao.deleteAll();
	}
	
	// save
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		roleDao.save( createNewEntity() );
	}
	
	// find by
	
	@Test
	public void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound(){
		// Given
		final String name = randomAlphabetic( 8 );
		
		// When
		final Role entityByName = roleDao.findByName( name );
		
		// Then
		assertNull( entityByName );
	}
	
	// template method
	
	@Override
	protected final IRoleJpaDAO getDAO(){
		return roleDao;
	}
	
	@Override
	protected final Role createNewEntity(){
		final Role entity = new Role( randomAlphabetic( 8 ) );
		entity.setPrivileges( Sets.<Privilege> newHashSet() );
		return entity;
	}
	
}
