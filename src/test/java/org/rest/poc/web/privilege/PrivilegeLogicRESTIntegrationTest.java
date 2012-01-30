package org.rest.poc.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Privilege;
import org.rest.poc.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegeLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< Privilege >{
	
	@Autowired
	PrivilegeRESTTemplateImpl restTemplate;
	
	@Autowired
	private ExamplePaths paths;
	
	public PrivilegeLogicRESTIntegrationTest(){
		super( Privilege.class );
	}
	
	// tests
	
	// util
	
	@Override
	protected final Privilege createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
	@Override
	protected final String getURI(){
		return paths.getUserUri() + "/";
	}
	
	@Override
	protected void change( final Privilege resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected void makeInvalid( final Privilege resource ){
		resource.setName( null );
	}
	
	@Override
	protected RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}
	
}
