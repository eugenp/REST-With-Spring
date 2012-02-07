package org.rest.sec.persistence.service.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.dao.IPrivilegeJpaDAO;
import org.rest.test.AbstractServiceUnitTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class PrivilegeServiceUnitTest extends AbstractServiceUnitTest< Privilege >{
	
	private PrivilegeServiceImpl instance;
	
	private IPrivilegeJpaDAO daoMock;
	
	public PrivilegeServiceUnitTest(){
		super( Privilege.class );
	}
	
	// fixtures
	
	@Before
	public final void before(){
		instance = new PrivilegeServiceImpl();
		
		daoMock = mock( IPrivilegeJpaDAO.class );
		when( daoMock.findAll() ).thenReturn( Lists.<Privilege> newArrayList() );
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
	
	final Privilege configureGet( final long id ){
		final Privilege entity = new Privilege();
		entity.setId( id );
		when( daoMock.findOne( id ) ).thenReturn( entity );
		return entity;
	}
	
	// template method
	
	@Override
	protected final PrivilegeServiceImpl getService(){
		return instance;
	}
	@Override
	protected final JpaRepository< Privilege, Long > getDAOMock(){
		return daoMock;
	}
	@Override
	protected final Privilege createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	final Privilege createNewEntity( final String name ){
		return new Privilege( name );
	}
	
}
