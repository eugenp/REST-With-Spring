package org.rest.integration;

import org.rest.test.spring.RESTPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ExamplePaths{
	
	@Autowired
	RESTPaths paths;
	
	// API
	
	public final String getFooURL(){
		return this.paths.getContext() + "/api/admin/foo";
	}
	
	public final String getLoginURL(){
		return this.paths.getContext() + "/j_spring_security_check";
	}
	
}
