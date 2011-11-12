package org.rest.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public final class SingleResourceRetrieved extends ApplicationEvent{
	private final HttpServletResponse response;
	private final HttpServletRequest request;
	
	public SingleResourceRetrieved( final Object sourceToSet, final HttpServletRequest requestToSet, final HttpServletResponse responseToSet ){
		super( sourceToSet );
		
		this.request = requestToSet;
		this.response = responseToSet;
	}
	
	//
	
	public final HttpServletRequest getRequest(){
		return this.request;
	}
	public final HttpServletResponse getResponse(){
		return this.response;
	}
	
}
