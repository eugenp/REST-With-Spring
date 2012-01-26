package org.rest.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ExamplePaths{
	
	@Autowired
	RESTPaths paths;
	
	// API
	
	public final String getRootUri(){
		return paths.getContext() + "/api/admin";
	}
	
	/**
	 * - note: there is no final '1'; if necessary, it needs to be added
	 */
	public final String getFooUri(){
		return getRootUri() + "/foo";
	}
	public final String getBarUri(){
		return getRootUri() + "/bar";
	}
	
	public final String getLoginUri(){
		return paths.getContext() + "/j_spring_security_check";
	}
	
}
