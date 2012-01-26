package org.rest.poc.testing.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Foo;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.SecurityUtil;
import org.rest.testing.template.AbstractRESTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jayway.restassured.specification.RequestSpecification;

/**
 * Template for the consumption of the REST API <br>
 */
@Component
public final class FooRESTTemplateImpl extends AbstractRESTTemplate< Foo >{
	
	@Autowired
	protected ExamplePaths paths;
	
	public FooRESTTemplateImpl(){
		super( Foo.class );
	}
	
	// template method
	
	@Override
	public final String getURI(){
		return paths.getFooUri();
	}
	
	@Override
	public final Foo createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return SecurityUtil.givenBasicAuthenticatedAsAdmin();
	}
	
	// util
	
	protected final Foo createNewEntity( final String name ){
		return new Foo( name );
	}
	
}
