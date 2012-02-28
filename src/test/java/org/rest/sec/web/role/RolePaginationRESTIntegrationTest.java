package org.rest.sec.web.role;

import org.rest.client.template.impl.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RolePaginationRESTIntegrationTest extends SecPaginationRESTIntegrationTest< Role >{
	
	@Autowired private RoleRESTTemplateImpl template;
	
	// tests
	
	// template method (shortcuts)
	
	@Override
	protected final Role createNewEntity(){
		return template.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return template.getURI();
	}
	
	@Override
	protected final RoleRESTTemplateImpl getTemplate(){
		return template;
	}
	
}
