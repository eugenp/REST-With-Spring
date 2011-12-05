package org.rest.common.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.google.common.base.Preconditions;

public final class DAOTestHelper{
	
	private DAOTestHelper(){
		super();
	}
	
	// API
	
	public static < T extends Serializable >void initialize( final AbstractDAO< T > dao, final SessionFactory sessionFactory ){
		Preconditions.checkNotNull( dao );
		Preconditions.checkNotNull( sessionFactory );
		Preconditions.checkState( dao.sessionFactory == null );
		
		dao.sessionFactory = sessionFactory;
	}
	
}
