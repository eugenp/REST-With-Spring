package org.rest.dao.impl;

import org.rest.common.dao.CustomHibernateDaoSupport;
import org.rest.dao.IFooDAO;
import org.rest.model.Foo;
import org.springframework.stereotype.Repository;

/**
 * @author eugenp
 */
@Repository
class FooDAO extends CustomHibernateDaoSupport< Foo > implements IFooDAO{
	
	public FooDAO(){
		super( Foo.class );
	}
	
	// API
	
}
