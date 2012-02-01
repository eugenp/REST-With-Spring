package org.rest.sec.testing.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.testing.template.AbstractRESTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * Template for the consumption of the REST API <br>
 */
@Component
public final class RoleRESTTemplateImpl extends AbstractRESTTemplate< Role >{
	
	@Autowired
	protected ExamplePaths paths;
	
	public RoleRESTTemplateImpl(){
		super( Role.class );
	}
	
	// template method
	
	@Override
	public final String getURI(){
		return paths.getRoleUri();
	}
	
	@Override
	public final Role createNewEntity(){
		return this.createNewEntity( randomAlphabetic( 8 ) );
	}
	
	@Override
	public final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticatedAsAdmin();
	}
	
	@Override
	public final void makeEntityInvalid( final Role entity ){
		entity.setName( null );
	}
	
	// util
	
	public final Role createNewEntity( final String name ){
		final Role newRole = new Role( name );
		newRole.setPrivileges( Sets.<Privilege> newHashSet() );
		return newRole;
	}
	
}
