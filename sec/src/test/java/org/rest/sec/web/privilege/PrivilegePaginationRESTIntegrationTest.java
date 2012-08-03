package org.rest.sec.web.privilege;

import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegePaginationRESTIntegrationTest extends SecPaginationRESTIntegrationTest< Privilege >{
	
	@Autowired private PrivilegeRESTTemplateImpl template;
	
	// tests
	
	// template method (shortcuts)
	
	@Override
	protected final Privilege createNewEntity(){
		return template.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return template.getURI();
	}
	
	@Override
	protected final PrivilegeRESTTemplateImpl getAPI(){
		return template;
	}
	
}
