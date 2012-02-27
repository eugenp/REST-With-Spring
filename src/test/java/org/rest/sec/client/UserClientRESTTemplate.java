package org.rest.sec.client;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.client.AbstractClientRESTTemplate;
import org.rest.client.ExamplePaths;
import org.rest.sec.dto.User;
import org.rest.sec.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public class UserClientRESTTemplate extends AbstractClientRESTTemplate< User >{
	
	@Autowired private ExamplePaths paths;
	
	public UserClientRESTTemplate(){
		super( User.class );
	}
	
	//
	
	// template method
	
	@Override
	public final String getURI(){
		return paths.getUserUri();
	}
	
	// entity
	
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
