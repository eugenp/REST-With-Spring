package org.rest.poc.web.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Foo;
import org.rest.poc.testing.template.FooRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.SecurityUtil;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.specification.RequestSpecification;

public class FooLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< Foo >{
	
	@Autowired
	FooRESTTemplateImpl restTemplate;
	
	@Autowired
	private ExamplePaths paths;
	
	public FooLogicRESTIntegrationTest(){
		super( Foo.class );
	}
	
	// tests
	
	// util
	
	@Override
	protected final Foo createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final FooRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
	@Override
	protected final String getURI(){
		return paths.getFooUri() + "/";
	}
	
	@Override
	protected void change( final Foo resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	
	@Override
	protected void makeInvalid( final Foo resource ){
		resource.setName( null );
	}
	
	@Override
	protected RequestSpecification givenAuthenticated(){
		return SecurityUtil.givenBasicAuthenticatedAsAdmin();
	}
	
}
