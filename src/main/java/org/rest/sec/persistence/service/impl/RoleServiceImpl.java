package org.rest.sec.persistence.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.rest.persistence.service.AbstractService;
import org.rest.sec.model.Role;
import org.rest.sec.model.Role_;
import org.rest.sec.persistence.dao.IRoleJpaDAO;
import org.rest.sec.persistence.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService< Role > implements IRoleService{
	
	@Autowired IRoleJpaDAO dao;
	
	public RoleServiceImpl(){
		super( Role.class );
	}
	
	// API
	
	// sandbox
	
	@Override
	public List< Role > search( final ImmutablePair< String, ? >... constraints ){
		final Specification< Role > firstSpec = resolveConstraint( constraints[0] );
		Specifications< Role > specifications = Specifications.where( firstSpec );
		for( int i = 1; i < constraints.length; i++ ){
			specifications = specifications.and( resolveConstraint( constraints[i] ) );
		}
		if( firstSpec == null ){
			return Lists.newArrayList();
		}
		
		return getDao().findAll( specifications );
	}
	private Specification< Role > resolveConstraint( final ImmutablePair< String, ? > constraint ){
		String constraintName = constraint.getLeft();
		boolean negated = false;
		if( constraintName.startsWith( "~" ) ){
			negated = true;
			constraintName = constraintName.substring( 1 );
		}
		
		if( constraintName.equals( "name" ) ){
			return byName( (String) constraint.getRight(), negated );
		}
		if( constraintName.equals( "id" ) ){
			return byId( (Long) constraint.getRight(), negated );
		}
		return null;
	}

	// search
	
	public static Specification< Role > byId( final Long id, final boolean negated ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notEqual( root.get( Role_.id ), id );
				}
				return cb.equal( root.get( Role_.id ), id );
			}
		};
	}
	public static Specification< Role > byName( final String name, final boolean negated ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notEqual( root.get( Role_.name ), name );
				}
				return cb.equal( root.get( Role_.name ), name );
			}
		};
	}
	
	// get/find
	
	@Override
	public Role findByName( final String name ){
		return dao.findByName( name );
	}
	
	// create
	
	@Override
	public Role create( final Role entity ){
		/*long id = new Random().nextLong() * 10000;
		id = ( id < 0 ) ? ( -1 * id ) : id;
		entity.setId( id );*/
		
		/*final List< Privilege > associationsTemp = Lists.newArrayList( entity.getPrivileges() );
		entity.getPrivileges().clear();
		for( final Privilege privilege : associationsTemp ){
			entity.getPrivileges().add( associationDao.findByName( privilege.getName() ) );
		}*/
		
		return super.create( entity );
	}
	
	// Spring
	
	@Override
	protected final IRoleJpaDAO getDao(){
		return dao;
	}
	
}
