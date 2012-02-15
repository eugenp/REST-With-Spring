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
	
	@Value( "${war}" )
	String war;
	
	// API
	
	public final String getServerRoot(){
		return protocol + "://" + host + ":" + port;
	}
	
	public final String getContext(){
		return protocol + "://" + host + ":" + port + war;
	}
	
}
