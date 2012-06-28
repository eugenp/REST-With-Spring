package org.rest.sec.persistence.service;

import org.rest.persistence.AbstractSearchPersistenceIntegrationTest;
import org.rest.sec.model.Role;
import org.rest.sec.persistence.util.FixtureFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleSearchPersistenceIntegrationTest extends AbstractSearchPersistenceIntegrationTest< Role >{
	
	@Autowired private IRoleService roleService;
	
	// tests
	
	// template method
	
	@Override
	protected final IRoleService getAPI(){
		return roleService;
	}
	
	@Override
	protected final Role createNewEntity(){
		return FixtureFactory.createNewRole();
	}
	
}
