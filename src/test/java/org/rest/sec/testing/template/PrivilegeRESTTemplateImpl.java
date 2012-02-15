package org.rest.sec.testing.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.sec.model.Privilege;
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
	
	// template method
	
	@Override
	public final String getURI(){
		return paths.getPrivilegeUri();
	}
	
	@Override
	public final Privilege createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	@Override
	public final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticated();
	}
	
	@Override
	public final void makeEntityInvalid( final Privilege entity ){
		entity.setName( null );
	}
	
	// util
	
	public final Privilege createNewEntity( final String name ){
		return new Privilege( name );
	}
	
}
