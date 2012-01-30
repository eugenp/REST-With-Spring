package org.rest.poc.web.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.User;
import org.rest.poc.testing.template.UserRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class UserLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< User >{
	
	@Autowired
	UserRESTTemplateImpl restTemplate;
	
	@Autowired
	private ExamplePaths paths;
	
	public UserLogicRESTIntegrationTest(){
		super( User.class );
	}
	
	// tests
	
	// util
	
	@Override
	protected final User createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final UserRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
	@Override
	protected final String getURI(){
		return paths.getUserUri() + "/";
	}
	
	@Override
	protected void change( final User resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected void makeInvalid( final User resource ){
		resource.setName( null );
	}
	
	@Override
	protected RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}
	
}
