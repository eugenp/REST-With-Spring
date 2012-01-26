package org.rest.poc.web.bar;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Bar;
import org.rest.poc.testing.template.BarRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.SecurityUtil;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class BarLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< Bar >{
	
	@Autowired
	BarRESTTemplateImpl restTemplate;
	
	@Autowired
	private ExamplePaths paths;
	
	public BarLogicRESTIntegrationTest(){
		super( Bar.class );
	}
	
	// tests
	
	// util
	
	@Override
	protected final Bar createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final BarRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
	@Override
	protected final String getURI(){
		return paths.getFooUri() + "/";
	}
	
	@Override
	protected void change( final Bar resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected void makeInvalid( final Bar resource ){
		resource.setName( null );
	}
	
	@Override
	protected RequestSpecification givenAuthenticated(){
		return SecurityUtil.givenBasicAuthenticatedAsAdmin();
	}
	
}
