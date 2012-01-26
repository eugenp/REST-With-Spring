package org.rest.persistence.event;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

import com.google.common.base.Preconditions;

public final class EntityCreatedEvent< T extends Serializable > extends ApplicationEvent{
	private final Class< T > clazz;
	private final T entity;
	
	public EntityCreatedEvent( final Object sourceToSet, final Class< T > clazzToSet, final T entityToSet ){
		super( sourceToSet );
		
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
		
		Preconditions.checkNotNull( entityToSet );
		this.entity = entityToSet;
	}
	
	// API
	
	public final Class< T > getClazz(){
		return clazz;
	}
	
	public final T getEntity(){
		return Preconditions.checkNotNull( entity );
	}
	
}
