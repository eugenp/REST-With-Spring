package org.rest.service.foo.impl;

import java.util.List;

import org.rest.common.dao.IGenericDAO;
import org.rest.common.util.RestPreconditions;
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
@Service
@Transactional( propagation = Propagation.REQUIRED )
class FooService implements IFooService{
	
	IGenericDAO< Foo > dao;
	
	public FooService(){
		super();
	}
	
	// Spring
	
	@Autowired
	public final void setDao( final IGenericDAO< Foo > daoToSet ){
		this.dao = daoToSet;
		this.dao.setClazz( Foo.class );
	}
	
	// API
	
	@Override
	public Foo getById( final Long id ){
		return this.dao.getById( id );
	}
	
	@Override
	public void create( final Foo entity ){
		this.dao.create( entity );
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
		RestPreconditions.checkNotNull( entity );
		
		this.delete( entity );
	}
	
}
