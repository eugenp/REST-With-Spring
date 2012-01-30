package org.rest.poc.web.privilege;

import org.rest.poc.model.Privilege;
import org.rest.poc.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< Privilege >{
	
	@Autowired
	PrivilegeRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}
