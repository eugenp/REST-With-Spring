package org.rest.sec.web.user;

import org.rest.sec.client.template.UserRESTTemplateImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.test.SecPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPaginationRESTIntegrationTest extends SecPaginationRESTIntegrationTest< User >{
	
	@Autowired private UserRESTTemplateImpl template;
	
	// tests
	
	// template method (shortcuts)
	
	@Override
	protected final User createNewEntity(){
		return template.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return template.getURI();
	}
	
	@Override
	protected final UserRESTTemplateImpl getAPI(){
		return template;
	}
	
}
