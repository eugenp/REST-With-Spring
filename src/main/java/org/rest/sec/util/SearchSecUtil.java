package org.rest.sec.util;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.rest.common.ClientOperation;
import org.rest.common.IEntity;
import org.rest.util.SearchCommonUtil;
import org.springframework.data.jpa.domain.Specification;

public final class SearchSecUtil{
	
	private SearchSecUtil(){
		throw new UnsupportedOperationException();
	}
	
	// util
	
	public static < T extends IEntity >Specification< T > resolveConstraint( final ImmutableTriple< String, ClientOperation, ? > constraint, final Class< T > clazz ){
		final String constraintName = constraint.getLeft();
		final boolean negated = isConstraintNegated( constraint );
		
		if( constraintName.equals( SearchCommonUtil.NAME ) ){
			return QuerySpecifications.getByNameSpecification( clazz, (String) constraint.getRight(), negated );
		}
		if( constraintName.equals( SearchCommonUtil.ID ) ){
			return QuerySpecifications.getByIdSpecification( clazz, (Long) constraint.getRight(), negated );
		}
		return null;
	}
	
	static boolean isConstraintNegated( final ImmutableTriple< String, ClientOperation, ? > constraint ){
		return constraint.getMiddle().isNegated();
	}

}
