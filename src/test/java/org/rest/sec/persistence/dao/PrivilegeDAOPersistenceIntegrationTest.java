package org.rest.sec.persistence.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.rest.persistence.AbstractPersistenceDAOIntegrationTest;
import org.rest.sec.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration( defaultRollback = true )
@Transactional
public class PrivilegeDAOPersistenceIntegrationTest extends AbstractPersistenceDAOIntegrationTest< Privilege >{
	
	@Autowired private IPrivilegeJpaDAO privilegeDao;
	@Autowired private IRoleJpaDAO associationDao;
	@Autowired private IPrincipalJpaDAO principalDao;
	
	// fixtures
	
	/**
	 * - note: temporary, until: https://github.com/eugenp/REST/issues/7
	 */
	@Before
	public final void before(){
		privilegeDao.deleteAll();
		associationDao.deleteAll();
		principalDao.deleteAll();
	}
	
	// save
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		getDAO().save( createNewEntity() );
	}
	
	// find by
	
	@Test
	public void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound(){
		// Given
		final String name = randomAlphabetic( 8 );
		
		// When
		final Privilege entityByName = getDAO().findByName( name );
		
		// Then
		assertNull( entityByName );
	}
	
	// template method
	
	@Override
	protected final IPrivilegeJpaDAO getDAO(){
		return privilegeDao;
	}
	
	@Override
	protected final Privilege createNewEntity(){
		final Privilege entity = new Privilege( randomAlphabetic( 8 ) );
		return entity;
	}
	
}
