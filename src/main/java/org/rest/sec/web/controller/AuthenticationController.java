package org.rest.sec.web.controller;

import java.util.Collection;

import org.rest.sec.dto.User;
import org.rest.sec.model.Privilege;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

/**
 * - note: this controller will start working with the User model and, if necessary, will move to a Authentication resource (which is the way it should work)
 */
@Controller
public class AuthenticationController{
	
	public AuthenticationController(){
		super();
	}
	
	// API
	
	@RequestMapping( value = "authentication",method = RequestMethod.GET )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody
	public User getAuthentication(){
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		final User authenticationResource = new User( authentication.getName(), null );
		
		final Function< GrantedAuthority, Privilege > springAuthorityToPrivilegeFunction = new Function< GrantedAuthority, Privilege >(){
			@Override
			public final Privilege apply( final GrantedAuthority springAuthority ){
				return new Privilege( springAuthority.getAuthority() );
			}
		};
		final Collection< Privilege > auths = Collections2.transform( authentication.getAuthorities(), springAuthorityToPrivilegeFunction );
		// TODO: implement this
		// authenticationResource.setRoles( Sets.<Role> newHashSet( auths ) );
		
		return authenticationResource;
	}
	
}
