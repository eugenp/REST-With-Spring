package org.rest.service.impl;

import org.rest.dao.IFooDAO;
import org.rest.model.Foo;
import org.rest.service.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eugenp
 */
@Service
@Transactional( propagation = Propagation.REQUIRED )
class FooService implements IFooService{
	
	@Autowired
	IFooDAO fooDAO;
	
	public FooService(){
		super();
	}
	
	// API
	
	@Override
	public Foo getById( final Long id ){
		return this.fooDAO.getById( id );
	}
	
	@Override
	public Foo getByIdExperimental( final Long id ){
		return this.fooDAO.getByIdExperimental( id );
	}
	
}
