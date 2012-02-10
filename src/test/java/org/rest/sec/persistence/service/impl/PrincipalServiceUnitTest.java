package org.rest.sec.persistence.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.rest.sec.model.Principal;
import org.rest.sec.persistence.dao.IPrincipalJpaDAO;
import org.rest.test.AbstractServiceUnitTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class PrincipalServiceUnitTest extends AbstractServiceUnitTest< Principal >{
	
	PrincipalServiceImpl instance;
	
	private IPrincipalJpaDAO daoMock;
	
	public PrincipalServiceUnitTest(){
		super( Principal.class );
	}
	
	// fixtures
	
	@Before
	public final void before(){
		instance = new PrincipalServiceImpl();
		
		daoMock = mock( IPrincipalJpaDAO.class );
		when( daoMock.findAll() ).thenReturn( Lists.<Principal> newArrayList() );
		instance.dao = daoMock;
	}
	
	// get
	
	@Test
	public final void whenGetIsTriggered_thenNoException(){
		configureGet( 1l );
		
		// When
		instance.findOne( 1l );
		
		// Then
	}
	
	@Test
	public final void whenGetIsTriggered_thenEntityIsRetrieved(){
		configureGet( 1l );
		// When
		instance.findOne( 1l );
		
		// Then
		verify( daoMock ).findOne( 1l );
	}
	
	// mocking behavior
	
	final Principal configureGet( final long id ){
		final Principal entity = new Principal();
		entity.setId( id );
		when( daoMock.findOne( id ) ).thenReturn( entity );
		return entity;
	}
	
	// template method
	
	@Override
	protected final PrincipalServiceImpl getService(){
		return instance;
	}
	@Override
	protected final JpaRepository< Principal, Long > getDAOMock(){
		return daoMock;
	}
	@Override
	protected final Principal createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	final Principal createNewEntity( final String username ){
		return new Principal( username, randomAlphabetic( 8 ) );
	}
	
}
