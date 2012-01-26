package org.rest.testing.security;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;

public final class SecurityUtil{
	public static final String ADMIN_USERNAME = "eparaschiv";
	public static final String ADMIN_PASSWORD = "eparaschiv";
	
	private SecurityUtil(){
		throw new AssertionError();
	}
	
	// API
	
	public static RequestSpecification givenBasicAuthenticatedAsAdmin(){
		return RestAssured.given().auth().preemptive().basic( ADMIN_USERNAME, ADMIN_PASSWORD );
	}
	public static RequestSpecification givenBasicAuthenticated( final String username, final String password ){
		return RestAssured.given().auth().preemptive().basic( username, password );
	}
	
}
