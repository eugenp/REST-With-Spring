package org.rest.poc.web.user;

import org.rest.poc.model.User;
import org.rest.poc.testing.template.UserRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class UserPaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< User >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	UserRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final User createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return paths.getUserUri();
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}

}
