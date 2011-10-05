package org.rest.dao.hello.impl;

import org.rest.common.dao.CustomHibernateDaoSupport;
import org.rest.dao.hello.IHelloDAO;
import org.rest.model.Hello;
import org.springframework.stereotype.Repository;

/**
 * @author eugenp
 */
@Repository
public class HelloDAO extends CustomHibernateDaoSupport< Hello > implements IHelloDAO{
	
	public HelloDAO(){
		super( Hello.class );
	}
	
	// API
	
}
