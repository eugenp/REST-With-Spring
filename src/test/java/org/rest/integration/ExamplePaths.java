package org.rest.integration;

import org.rest.test.RESTPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ExamplePaths{
	
	@Autowired
	RESTPaths paths;
	
	// API
	
	public final String getRootURL(){
		return this.paths.getContext() + "/api/admin";
	}
	
	public final String getFooURL(){
		return this.getRootURL() + "/foo";
	}
	
	public final String getLoginURL(){
		return this.paths.getContext() + "/j_spring_security_check";
	}
	
}
