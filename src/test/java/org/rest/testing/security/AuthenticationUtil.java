package org.rest.testing.security;

import org.rest.sec.util.SecurityConstants;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;

public final class AuthenticationUtil{
	
	private AuthenticationUtil(){
		throw new AssertionError();
	}
	
	// API
	
	public static RequestSpecification givenBasicAuthenticatedAsAdmin(){
		return RestAssured.given().auth().preemptive().basic( SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD );
	}
	public static RequestSpecification givenBasicAuthenticated( final String username, final String password ){
		return RestAssured.given().auth().preemptive().basic( username, password );
	}
	
}
