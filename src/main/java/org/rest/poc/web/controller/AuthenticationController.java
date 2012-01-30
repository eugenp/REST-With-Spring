package org.rest.poc.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.rest.persistence.service.IService;
import org.rest.poc.model.User;
import org.rest.poc.persistence.service.user.IUserService;
import org.rest.web.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AuthenticationController extends AbstractController< User >{
	
	@Autowired
	private IUserService service;
	
	public AuthenticationController(){
		super( User.class );
	}
	
	// API
	
	@RequestMapping( value = "admin/authentication",method = GET )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody
	public User authenticate(){
		final Authentication authenticationInSpring = SecurityContextHolder.getContext().getAuthentication();
		
		// - note: no need to publish the roles at this point
		// authentication.setPrivileges( Sets.<Privilege> newHashSet() );
		
		final User authentication = new User( authenticationInSpring.getName() );
		return authentication;
	}
	
	//
	
	@Override
	protected final IService< User > getService(){
		return service;
	}
	
}
