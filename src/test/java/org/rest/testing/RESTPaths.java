package org.rest.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class RESTPaths{
	
	@Value( "${protocol}" )
	String protocol;
	
	@Value( "${host}" )
	String host;
	
	@Value( "${port}" )
	String port;
	
	@Value( "${path}" )
	String path;
	
	// API
	
	public final String getServerRoot(){
		return this.protocol + "://" + this.host + ":" + this.port;
	}
	
	public final String getContext(){
		return this.protocol + "://" + this.host + ":" + this.port + this.path;
	}
	
}
