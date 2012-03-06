package org.rest.client.template.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.client.template.AbstractRESTTemplate;
import org.rest.sec.client.ExamplePaths;
import org.rest.sec.dto.User;
import org.rest.sec.model.Role;
import org.rest.testing.security.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.jayway.restassured.specification.RequestSpecification;

@Component
public final class UserRESTTemplateImpl extends AbstractRESTTemplate< User >{
	
	@Autowired protected ExamplePaths paths;
	
	public UserRESTTemplateImpl(){
		super( User.class );
	}
	
	// API
	
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
	public final void invalidate( final User entity ){
		entity.setName( null );
	}
	@Override
	public final void change( final User resource ){
		resource.setName( randomAlphabetic( 8 ) );
	}
	
}
