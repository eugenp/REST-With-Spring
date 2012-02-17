package org.rest.sec.client;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.client.AbstractClientRestTemplate;
import org.rest.sec.dto.User;
import org.rest.sec.model.Role;
import org.rest.testing.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public class UserRESTTemplate extends AbstractClientRestTemplate< User >{
	
	@Autowired private ExamplePaths paths;
	
	public UserRESTTemplate(){
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
	public final void makeEntityInvalid( final User entity ){
		entity.setName( null );
	}
	@Override
	public final void change( final User resource ){
		resource.setName( randomAlphabetic( 8 ) );
	}
	
}
