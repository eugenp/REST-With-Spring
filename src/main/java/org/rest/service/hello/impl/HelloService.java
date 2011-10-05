package org.rest.service.hello.impl;

import java.util.List;

import org.rest.dao.hello.IHelloDAO;
import org.rest.exceptions.ResourceNotFoundException;
import org.rest.model.Hello;
import org.rest.service.hello.IHelloService;
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
class HelloService implements IHelloService{
	
	@Autowired
	IHelloDAO dao;
	
	public HelloService(){
		super();
	}
	
	// API
	
	@Override
	public Hello getById( final Long id ){
		return this.dao.getById( id );
	}
	
	@Override
	public Hello getByIdExperimental( final Long id ){
		return this.dao.getByIdExperimental( id );
	}
	
	@Override
	public Long create( final Hello entity ){
		return this.dao.create( entity );
	}
	
	@Override
	public void update( final Hello entity ){
		this.dao.update( entity );
	}
	
	@Override
	public void delete( final Hello entity ){
		this.dao.delete( entity );
	}
	
	@Override
	public List< Hello > getAll(){
		return this.dao.getAll();
	}
	
	// service specific
	
	@Override
	public void deleteById( final Long id ){
		Preconditions.checkNotNull( id );
		
		final Hello entity = this.getById( id );
		if( entity == null ){
			throw new ResourceNotFoundException();
		}
		this.delete( entity );
	}
	
}
