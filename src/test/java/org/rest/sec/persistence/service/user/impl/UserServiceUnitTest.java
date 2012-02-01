package org.rest.sec.persistence.service.user.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.rest.sec.model.User;
import org.rest.sec.persistence.dao.IUserJpaDAO;
import org.rest.test.AbstractServiceUnitTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public class UserServiceUnitTest extends AbstractServiceUnitTest< User >{
	
	UserServiceImpl instance;
	
	private IUserJpaDAO daoMock;
	
	public UserServiceUnitTest(){
		super( User.class );
	}
	
	// fixtures
	
	@Before
	public final void before(){
		instance = new UserServiceImpl();
		
		daoMock = mock( IUserJpaDAO.class );
		when( daoMock.findAll() ).thenReturn( Lists.<User> newArrayList() );
		instance.dao = daoMock;
		
		instance.setEventPublisher( mock( ApplicationEventPublisher.class ) );
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
	
	final User configureGet( final long id ){
		final User entity = new User();
		entity.setId( id );
		when( daoMock.findOne( id ) ).thenReturn( entity );
		return entity;
	}
	
	// template method
	
	@Override
	protected final UserServiceImpl getService(){
		return instance;
	}
	@Override
	protected final JpaRepository< User, Long > getDAOMock(){
		return daoMock;
	}
	@Override
	protected final User createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	// util
	
	final User createNewEntity( final String username ){
		return new User( username, randomAlphabetic( 8 ) );
	}
	
}
