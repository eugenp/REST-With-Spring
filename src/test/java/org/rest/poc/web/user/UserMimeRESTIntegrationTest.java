package org.rest.poc.web.user;

import org.rest.poc.model.User;
import org.rest.poc.testing.template.UserRESTTemplateImpl;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< User >{
	
	@Autowired
	UserRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final UserRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}
