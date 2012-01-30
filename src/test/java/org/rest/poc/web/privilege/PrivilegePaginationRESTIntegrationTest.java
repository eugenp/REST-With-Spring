package org.rest.poc.web.privilege;

import org.rest.poc.model.Privilege;
import org.rest.poc.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegePaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< Privilege >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	PrivilegeRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final Privilege createNewEntity(){
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
