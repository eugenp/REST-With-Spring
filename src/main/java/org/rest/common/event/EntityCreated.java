package org.rest.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class EntityCreated extends ApplicationEvent{
	private final HttpServletResponse response;
	private final long idOfNewEntity;
	private final HttpServletRequest request;
	
	public EntityCreated( final Object sourceToSet, final HttpServletRequest requestToSet, final HttpServletResponse responseToSet, final long idOfNewEntityToSet ){
		super( sourceToSet );
		
		this.request = requestToSet;
		this.response = responseToSet;
		this.idOfNewEntity = idOfNewEntityToSet;
	}
	
	//
	
	public final HttpServletRequest getRequest(){
		return this.request;
	}
	public final HttpServletResponse getResponse(){
		return this.response;
	}
	public final long getIdOfNewEntity(){
		return this.idOfNewEntity;
	}
	
}
