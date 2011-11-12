package org.rest.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public final class ResourceCreated extends ApplicationEvent{
	private final HttpServletResponse response;
	private final long idOfNewResource;
	private final HttpServletRequest request;
	
	public ResourceCreated( final Object sourceToSet, final HttpServletRequest requestToSet, final HttpServletResponse responseToSet, final long idOfNewResourceToSet ){
		super( sourceToSet );
		
		this.request = requestToSet;
		this.response = responseToSet;
		this.idOfNewResource = idOfNewResourceToSet;
	}
	
	//
	
	public final HttpServletRequest getRequest(){
		return this.request;
	}
	public final HttpServletResponse getResponse(){
		return this.response;
	}
	public final long getIdOfNewResource(){
		return this.idOfNewResource;
	}
	
}
