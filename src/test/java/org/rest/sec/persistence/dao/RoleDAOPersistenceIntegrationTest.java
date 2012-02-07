package org.rest.sec.persistence.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

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
	
	@Autowired
	private IRoleJpaDAO dao;
	
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
		final Role entityByName = dao.findByName( name );
		
		// Then
		assertNull( entityByName );
	}
	
	// template method
	
	@Override
	protected final IRoleJpaDAO getDAO(){
		return dao;
	}
	
	@Override
	protected final Role createNewEntity(){
		final Role entity = new Role( randomAlphabetic( 8 ) );
		entity.setPrivileges( Sets.<Privilege> newHashSet() );
		return entity;
	}
	
}
