package org.rest.sec.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.persistence.AbstractPersistenceServiceIntegrationTest;
import org.rest.persistence.service.IService;
import org.rest.sec.model.Privilege;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegeServicePersistenceIntegrationTest extends AbstractPersistenceServiceIntegrationTest< Privilege >{
	
	@Autowired private IPrivilegeService privilegeService;
	@Autowired IRoleService roleService;
	@Autowired IPrincipalService principalService;
	
	// create
	
	@Test
	public void whenSaveIsPerformed_thenNoException(){
		privilegeService.create( createNewEntity() );
	}
	
	@Test( expected = DataAccessException.class )
	public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown(){
		final String name = randomAlphabetic( 8 );
		
		privilegeService.create( this.createNewEntity( name ) );
		privilegeService.create( this.createNewEntity( name ) );
	}
	
	// template method
	
	@Override
	protected final IService< Privilege > getAPI(){
		return privilegeService;
	}
	@Override
	protected final Privilege createNewEntity(){
		return new Privilege( randomAlphabetic( 8 ) );
	}
	@Override
	protected final void invalidate( final Privilege entity ){
		entity.setName( null );
	}
	@Override
	protected final void changeEntity( final Privilege entity ){
		entity.setName( randomAlphabetic( 6 ) );
	}
	
	// util
	
	protected final Privilege createNewEntity( final String name ){
		return new Privilege( name );
	}
	
}
