package org.rest.sec.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.rest.common.ClientOperation;
import org.rest.common.IEntity;
import org.rest.common.util.QueryUtil;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Principal_;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Privilege_;
import org.rest.sec.model.Role;
import org.rest.sec.model.Role_;
import org.springframework.data.jpa.domain.Specification;

public final class QuerySpecifications{
	
	private QuerySpecifications(){
		throw new UnsupportedOperationException();
	}
	
	// API - retrieve specifications for all OPs and all supported classes
	
	public static < T extends IEntity >Specification< T > getByNameSpecification( final Class< T > clazz, final ClientOperation op, final String name, final boolean negated ){
		switch( op ){
		case EQ:
			return getByNameSpecificationEq( clazz, name, negated );
		case NEG_EQ:
			return getByNameSpecificationEq( clazz, name, negated );
			
		case CONTAINS:
			return getByNameSpecificationContains( clazz, name, negated );
		case STARTS_WITH:
			return getByNameSpecificationStartsWith( clazz, name, negated );
		case ENDS_WITH:
			return getByNameSpecificationEndsWith( clazz, name, negated );
			
		default:
			break;
		}
		
		throw new UnsupportedOperationException( "Received invalid operator: " + op );
	}
	
	// in between
	
	@SuppressWarnings( "unchecked" )
	static < T extends IEntity >Specification< T > getByIdSpecification( final Class< T > clazz, final Long id, final boolean negated ){
		if( clazz.equals( Role.class ) ){
			return (Specification< T >) roleByIdEq( id, negated );
		}
		if( clazz.equals( Principal.class ) ){
			return (Specification< T >) userByIdEq( id, negated );
		}
		if( clazz.equals( Privilege.class ) ){
			return (Specification< T >) privilegeByIdEq( id, negated );
		}
		
		return null;
	}
	
	@SuppressWarnings( "unchecked" )
	static < T extends IEntity >Specification< T > getByNameSpecificationEq( final Class< T > clazz, final String name, final boolean negated ){
		if( clazz.equals( Role.class ) ){
			return (Specification< T >) roleByNameEq( name, negated );
		}
		if( clazz.equals( Principal.class ) ){
			return (Specification< T >) userByNameEq( name, negated );
		}
		if( clazz.equals( Privilege.class ) ){
			return (Specification< T >) privilegeByNameEq( name, negated );
		}
		
		return null;
	}
	@SuppressWarnings( "unchecked" )
	static < T extends IEntity >Specification< T > getByNameSpecificationContains( final Class< T > clazz, final String name, final boolean negated ){
		if( clazz.equals( Role.class ) ){
			return (Specification< T >) roleByNameContains( name, negated );
		}
		if( clazz.equals( Principal.class ) ){
			return (Specification< T >) userByNameContains( name, negated );
		}
		if( clazz.equals( Privilege.class ) ){
			return (Specification< T >) privilegeByNameContains( name, negated );
		}
		
		return null;
	}
	@SuppressWarnings( "unchecked" )
	static < T extends IEntity >Specification< T > getByNameSpecificationStartsWith( final Class< T > clazz, final String name, final boolean negated ){
		if( clazz.equals( Role.class ) ){
			return (Specification< T >) roleByNameStartsWith( name, negated );
		}
		if( clazz.equals( Principal.class ) ){
			return (Specification< T >) userByNameStartsWith( name, negated );
		}
		if( clazz.equals( Privilege.class ) ){
			return (Specification< T >) privilegeByNameStartsWith( name, negated );
		}
		
		return null;
	}
	@SuppressWarnings( "unchecked" )
	static < T extends IEntity >Specification< T > getByNameSpecificationEndsWith( final Class< T > clazz, final String name, final boolean negated ){
		if( clazz.equals( Role.class ) ){
			return (Specification< T >) roleByNameEndsWith( name, negated );
		}
		if( clazz.equals( Principal.class ) ){
			return (Specification< T >) userByNameEndsWith( name, negated );
		}
		if( clazz.equals( Privilege.class ) ){
			return (Specification< T >) privilegeByNameEndsWith( name, negated );
		}
		
		return null;
	}
	
	// individual specifications
	
	// user
	
	private static Specification< Principal > userByIdEq( final Long id, final boolean negated ){
		return new Specification< Principal >(){
			@Override
			public final Predicate toPredicate( final Root< Principal > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notEqual( root.get( Principal_.id ), id );
				}
				return cb.equal( root.get( Principal_.id ), id );
			}
		};
	}
	
	private static Specification< Principal > userByNameEq( final String name, final boolean negated ){
		return new Specification< Principal >(){
			@Override
			public final Predicate toPredicate( final Root< Principal > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notEqual( root.get( Principal_.name ), name );
				}
				return cb.equal( root.get( Principal_.name ), name );
			}
		};
	}
	
	private static Specification< Principal > userByNameContains( final String name, final boolean negated ){
		return new Specification< Principal >(){
			@Override
			public final Predicate toPredicate( final Root< Principal > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Principal_.name ), QueryUtil.ANY_SERVER + name + QueryUtil.ANY_SERVER );
				}
				return cb.like( root.get( Principal_.name ), QueryUtil.ANY_SERVER + name + QueryUtil.ANY_SERVER );
			}
		};
	}
	private static Specification< Principal > userByNameStartsWith( final String name, final boolean negated ){
		return new Specification< Principal >(){
			@Override
			public final Predicate toPredicate( final Root< Principal > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Principal_.name ), name + QueryUtil.ANY_SERVER );
				}
				return cb.like( root.get( Principal_.name ), name + QueryUtil.ANY_SERVER );
			}
		};
	}
	private static Specification< Principal > userByNameEndsWith( final String name, final boolean negated ){
		return new Specification< Principal >(){
			@Override
			public final Predicate toPredicate( final Root< Principal > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Principal_.name ), QueryUtil.ANY_SERVER + name );
				}
				return cb.like( root.get( Principal_.name ), QueryUtil.ANY_SERVER + name );
			}
		};
	}
	
	// role
	
	private static Specification< Role > roleByIdEq( final Long id, final boolean negated ){
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
	
	private static Specification< Role > roleByNameEq( final String name, final boolean negated ){
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
	private static Specification< Role > roleByNameContains( final String name, final boolean negated ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Role_.name ), QueryUtil.ANY_SERVER + name + QueryUtil.ANY_SERVER );
				}
				return cb.like( root.get( Role_.name ), QueryUtil.ANY_SERVER + name + QueryUtil.ANY_SERVER );
			}
		};
	}
	private static Specification< Role > roleByNameEndsWith( final String name, final boolean negated ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Role_.name ), QueryUtil.ANY_SERVER + name );
				}
				return cb.like( root.get( Role_.name ), QueryUtil.ANY_SERVER + name );
			}
		};
	}
	private static Specification< Role > roleByNameStartsWith( final String name, final boolean negated ){
		return new Specification< Role >(){
			@Override
			public final Predicate toPredicate( final Root< Role > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Role_.name ), name + QueryUtil.ANY_SERVER );
				}
				return cb.like( root.get( Role_.name ), name + QueryUtil.ANY_SERVER );
			}
		};
	}
	
	// privilege
	
	private static Specification< Privilege > privilegeByIdEq( final Long id, final boolean negated ){
		return new Specification< Privilege >(){
			@Override
			public final Predicate toPredicate( final Root< Privilege > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notEqual( root.get( Privilege_.id ), id );
				}
				return cb.equal( root.get( Privilege_.id ), id );
			}
		};
	}
	
	private static Specification< Privilege > privilegeByNameEq( final String name, final boolean negated ){
		return new Specification< Privilege >(){
			@Override
			public final Predicate toPredicate( final Root< Privilege > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notEqual( root.get( Privilege_.name ), name );
				}
				return cb.equal( root.get( Privilege_.name ), name );
			}
		};
	}
	private static Specification< Privilege > privilegeByNameContains( final String name, final boolean negated ){
		return new Specification< Privilege >(){
			@Override
			public final Predicate toPredicate( final Root< Privilege > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Privilege_.name ), QueryUtil.ANY_SERVER + name + QueryUtil.ANY_SERVER );
				}
				return cb.like( root.get( Privilege_.name ), QueryUtil.ANY_SERVER + name + QueryUtil.ANY_SERVER );
			}
		};
	}
	private static Specification< Privilege > privilegeByNameStartsWith( final String name, final boolean negated ){
		return new Specification< Privilege >(){
			@Override
			public final Predicate toPredicate( final Root< Privilege > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Privilege_.name ), name + QueryUtil.ANY_SERVER );
				}
				return cb.like( root.get( Privilege_.name ), name + QueryUtil.ANY_SERVER );
			}
		};
	}
	private static Specification< Privilege > privilegeByNameEndsWith( final String name, final boolean negated ){
		return new Specification< Privilege >(){
			@Override
			public final Predicate toPredicate( final Root< Privilege > root, final CriteriaQuery< ? > query, final CriteriaBuilder cb ){
				if( negated ){
					return cb.notLike( root.get( Privilege_.name ), QueryUtil.ANY_SERVER + name );
				}
				return cb.like( root.get( Privilege_.name ), QueryUtil.ANY_SERVER + name );
			}
		};
	}
	
}
