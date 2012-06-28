package org.rest.sec.persistence.service;

import org.junit.runner.RunWith;
import org.rest.persistence.AbstractSearchPersistenceIntegrationTest;
import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.util.FixtureFactory;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.spring.testing.TestingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegeSearchPersistenceIntegrationTest extends AbstractSearchPersistenceIntegrationTest< Privilege >{
	
	@Autowired private IPrivilegeService privilegeService;
	
	// tests
	
	// template method
	
	@Override
	protected final IPrivilegeService getAPI(){
		return privilegeService;
	}
	
	@Override
	protected final Privilege createNewEntity(){
		return FixtureFactory.createNewPrivilege();
	}
	
}
