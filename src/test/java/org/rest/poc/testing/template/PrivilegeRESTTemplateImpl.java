package org.rest.poc.testing.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.poc.model.Privilege;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.testing.template.AbstractRESTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jayway.restassured.specification.RequestSpecification;

/**
 * Template for the consumption of the REST API <br>
 */
@Component
public final class PrivilegeRESTTemplateImpl extends AbstractRESTTemplate< Privilege >{
	
	@Autowired
	protected ExamplePaths paths;
	
	public PrivilegeRESTTemplateImpl(){
		super( Privilege.class );
	}
	
	// API (and template methods)
	
	@Override
	public final String getURI(){
		return paths.getPrivilegeUri();
	}
	
	@Override
	public final Privilege createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}
	
	//
	
	public final Privilege createNewEntity( final String name ){
		final Privilege entity = new Privilege( name );
		return entity;
	}
	
}
