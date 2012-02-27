package org.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class RESTPaths{
	
	@Value( "${protocol}" ) private String protocol;
	@Value( "${host}" ) private String host;
	@Value( "${port}" ) private String port;
	@Value( "${war}" ) private String war;
	
	// API
	
	public final String getServerRoot(){
		return protocol + "://" + host + ":" + port;
	}
	
	public final String getContext(){
		return protocol + "://" + host + ":" + port + war;
	}
	
}
