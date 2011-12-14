package org.rest.common.dao.jpa;

import java.io.Serializable;

import org.rest.common.dao.IGenericDAO;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Profile( "jpa" )
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericJpaDAO< T extends Serializable > extends AbstractJpaDAO< T > implements IGenericDAO< T >{
	
	public GenericJpaDAO(){
		super();
	}
	
}
