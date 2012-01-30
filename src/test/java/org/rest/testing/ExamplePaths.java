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
	public final String getUserUri(){
		return getRootUri() + "/user";
	}
	public final String getPrivilegeUri(){
		return getRootUri() + "/privilege";
	}
	
	public final String getAuthenticationUri(){
		return getRootUri() + "/authentication";
	}
	
}
