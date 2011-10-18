package org.rest.service.foo.impl;

import java.util.List;

import org.rest.dao.foo.IFooDAO;
import org.rest.exceptions.ResourceNotFoundException;
import org.rest.model.Foo;
import org.rest.service.foo.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

/**
 * @author eugenp
 */
// TODO: later on this should not be public
@Service
@Transactional( propagation = Propagation.REQUIRED )
public class FooService implements IFooService{
	
	@Autowired
	IFooDAO dao;
	
	public FooService(){
		super();
	}
	
	// API
	
	@Override
	public Foo getById( final Long id ){
		return this.dao.getById( id );
	}
	
	@Override
	public Long create( final Foo entity ){
		return this.dao.create( entity );
	}
	
	@Override
	public void update( final Foo entity ){
		this.dao.update( entity );
	}
	
	@Override
	public void delete( final Foo entity ){
		this.dao.delete( entity );
	}
	
	@Override
	public List< Foo > getAll(){
		return this.dao.getAll();
	}
	
	// service specific
	
	@Override
	public void deleteById( final Long id ){
		Preconditions.checkNotNull( id );
		
		final Foo entity = this.getById( id );
		if( entity == null ){
			throw new ResourceNotFoundException();
		}
		this.delete( entity );
	}
	
}
