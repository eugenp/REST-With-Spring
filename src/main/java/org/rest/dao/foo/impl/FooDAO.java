package org.rest.dao.foo.impl;

import org.rest.common.dao.CustomHibernateDaoSupport;
import org.rest.dao.foo.IFooDAO;
import org.rest.model.Foo;
import org.springframework.stereotype.Repository;

/**
 * @author eugenp
 */
@Repository
public class FooDAO extends CustomHibernateDaoSupport< Foo > implements IFooDAO{
	
	public FooDAO(){
		super( Foo.class );
	}
	
	// API
	
}
