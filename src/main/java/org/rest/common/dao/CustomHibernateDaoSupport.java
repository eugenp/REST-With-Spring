package org.rest.common.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CustomHibernateDaoSupport< T extends Serializable > extends HibernateDaoSupport{
	
	protected Class< T > clazz;
	
	public CustomHibernateDaoSupport( final Class< T > theClassToSet ){
		super();
		
		this.clazz = theClassToSet;
	}
	
	//
	@Autowired
	public final void init( final SessionFactory factory ){
		this.setSessionFactory( factory );
	}
	
}
