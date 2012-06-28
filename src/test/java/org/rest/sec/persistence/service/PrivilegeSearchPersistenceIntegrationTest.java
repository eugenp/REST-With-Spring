package org.rest.sec.persistence.service;

import org.rest.persistence.AbstractSearchPersistenceIntegrationTest;
import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.util.FixtureFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
