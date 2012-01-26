package org.rest.poc.persistence.service.foo.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.poc.model.Foo;
import org.rest.poc.persistence.dao.foo.IFooJpaDAO;
import org.rest.poc.persistence.service.foo.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FooServiceImpl extends AbstractService< Foo > implements IFooService{
	
	public FooServiceImpl(){
		super( Foo.class );
	}
	
	@Autowired
	IFooJpaDAO dao;
	
	// API
	
	// get/find
	
	@Override
	@Transactional( readOnly = true )
	public Page< Foo > findPaginated( final int page, final int size ){
		return dao.findAll( new PageRequest( page, size ) );
	}
	
	// Spring
	
	@Override
	protected final IFooJpaDAO getDao(){
		return dao;
	}
	
}
