package org.rest.poc.web.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.User;
import org.rest.poc.testing.template.UserRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.common.AbstractRESTDiscoverabilityIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class UserDiscoverabilityRESTIntegrationTest extends AbstractRESTDiscoverabilityIntegrationTest< User >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	UserRESTTemplateImpl restTemplate;
	
	public UserDiscoverabilityRESTIntegrationTest(){
		super( User.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final String getURI(){
		return paths.getUserUri();
	}

	@Override
	protected final UserRESTTemplateImpl getTemplate(){
		return restTemplate;
	}

	@Override
	protected final void change( final User resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected final User createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}

}
