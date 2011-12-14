package org.rest.common.dao.hibernate;

import java.io.Serializable;

import org.rest.common.dao.IGenericDAO;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Profile( "hibernate" )
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericHibernateDAO< T extends Serializable > extends AbstractHibernateDAO< T > implements IGenericDAO< T >{
	
	public GenericHibernateDAO(){
		super();
	}
	
}
