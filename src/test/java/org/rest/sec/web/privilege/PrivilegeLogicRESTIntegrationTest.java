package org.rest.sec.web.privilege;

import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.test.SecLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegeLogicRESTIntegrationTest extends SecLogicRESTIntegrationTest< Privilege >{
	
	@Autowired private PrivilegeRESTTemplateImpl restTemplate;
	
	public PrivilegeLogicRESTIntegrationTest(){
		super( Privilege.class );
	}
	
	// tests
	
	// template
	
	@Override
	protected final PrivilegeRESTTemplateImpl getAPI(){
		return restTemplate;
	}
	
	@Override
	protected final String getURI(){
		return getAPI().getURI() + "/";
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return getAPI().givenAuthenticated();
	}
	
}
