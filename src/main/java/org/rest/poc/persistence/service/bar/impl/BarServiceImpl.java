package org.rest.poc.persistence.service.bar.impl;

import org.rest.persistence.service.AbstractService;
import org.rest.poc.model.Bar;
import org.rest.poc.persistence.dao.bar.IBarJpaDAO;
import org.rest.poc.persistence.service.bar.IBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BarServiceImpl extends AbstractService< Bar > implements IBarService{
	
	public BarServiceImpl(){
		super( Bar.class );
	}
	
	@Autowired
	IBarJpaDAO dao;
	
	// API
	
	@Override
	public Page< Bar > findPaginated( final int page, final int size ){
		return dao.findAll( new PageRequest( page, size ) );
	}
	
	// Spring
	
	@Override
	protected final IBarJpaDAO getDao(){
		return dao;
	}
	
}
