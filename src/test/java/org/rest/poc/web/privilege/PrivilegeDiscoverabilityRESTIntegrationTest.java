package org.rest.poc.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Privilege;
import org.rest.poc.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.web.common.AbstractRESTDiscoverabilityIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegeDiscoverabilityRESTIntegrationTest extends AbstractRESTDiscoverabilityIntegrationTest< Privilege >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	PrivilegeRESTTemplateImpl restTemplate;
	
	public PrivilegeDiscoverabilityRESTIntegrationTest(){
		super( Privilege.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final String getURI(){
		return paths.getUserUri();
	}
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
	@Override
	protected final void change( final Privilege resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected final Privilege createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}

}
