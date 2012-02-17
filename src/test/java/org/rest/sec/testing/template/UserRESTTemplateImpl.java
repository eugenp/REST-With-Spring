package org.rest.sec.testing.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.sec.dto.User;
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
public final class UserRESTTemplateImpl extends AbstractRESTTemplate< User >{
	
	@Autowired protected ExamplePaths paths;
	
	public UserRESTTemplateImpl(){
		super( User.class );
	}
	
	// template method
	
	@Override
	public final String getURI(){
		return paths.getUserUri();
	}
	
	@Override
	public final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticated();
	}
	
	@Override
	public final User createNewEntity(){
		return new User( randomAlphabetic( 8 ), randomAlphabetic( 8 ), Sets.<Role> newHashSet() );
	}
	@Override
	public final void makeEntityInvalid( final User entity ){
		entity.setName( null );
	}
	@Override
	public final void change( final User resource ){
		resource.setName( randomAlphabetic( 8 ) );
	}
	
}
