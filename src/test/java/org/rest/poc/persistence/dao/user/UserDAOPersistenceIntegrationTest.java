package org.rest.poc.persistence.dao.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.rest.persistence.AbstractPersistenceDaoIntegrationTest;
import org.rest.poc.model.User;
import org.rest.poc.persistence.dao.IUserJpaDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAOPersistenceIntegrationTest extends AbstractPersistenceDaoIntegrationTest< User >{
	
	@Autowired
	IUserJpaDAO dao;
	
	/*@Before
	public final void before(){
		this.dao.deleteAll();
	}*/

	// save
	
	@Test
	public final void whenSaveIsPerformed_thenNoException(){
		dao.save( new User( randomAlphabetic( 8 ) ) );
	}
	
	// find by
	
	@Test
	public final void givenEntityDoesNotExist_whenFindingEntityByName_thenEntityNotFound(){
		// Given
		final String name = randomAlphabetic( 8 );
		
		// When
		final User entityByName = dao.findByName( name );
		
		// Then
		assertNull( entityByName );
	}
	
	// template method
	
	@Override
	protected IUserJpaDAO getDAO(){
		return dao;
	}
	
	@Override
	protected final User createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	protected final User createNewEntity( final String name ){
		return new User( name );
	}
}
