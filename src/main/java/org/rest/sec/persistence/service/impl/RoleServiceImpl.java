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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService< Role > implements IRoleService{
	
	@Autowired IRoleJpaDAO dao;
	
	// @Autowired private IPrivilegeJpaDAO associationDao;
	
	public RoleServiceImpl(){
		super( Role.class );
	}
	
	// API
	
	// sandbox
	
	@Override
	public List< Role > search( final ImmutablePair< String, String >... constraints ){
		// Specifications.where( byName( "name" ) );
		return getDao().findAll( byName( constraints[0].getRight() ) );
	}
	
	// search
	
	@Override
	public List< Role > search( final Long id ){
		return getDao().findAll( byId( id ) );
	}
	
	public static Specification< Role > byId( final Long id ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				return cb.equal( root.get( Role_.id ), id );
			}
		};
	}
	public static Specification< Role > byName( final String name ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
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
