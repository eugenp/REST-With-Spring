package org.rest.poc.testing.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Bar;
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
public final class BarRESTTemplateImpl extends AbstractRESTTemplate< Bar >{
	
	@Autowired
	protected ExamplePaths paths;
	
	public BarRESTTemplateImpl(){
		super( Bar.class );
	}
	
	// API (and template methods)
	
	@Override
	public final String getURI(){
		return paths.getBarUri();
	}
	
	@Override
	public final Bar createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return SecurityUtil.givenBasicAuthenticatedAsAdmin();
	}
	
	//
	
	public final Bar createNewEntity( final String name ){
		final Bar entity = new Bar( name );
		return entity;
	}
	
}
