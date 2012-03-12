package org.rest.sec.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.rest.sec.model.Role;
import org.rest.sec.model.Role_;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class SearchUtil {

    public static final String SEPARATOR = ",";
    public static final String OP = "=";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NEGATION = "~";

    private SearchUtil() {
	throw new UnsupportedOperationException();
    }

    //

    public static List<ImmutablePair<String, ?>> parseQueryString(final String queryString) {
	Preconditions.checkNotNull(queryString);
	Preconditions.checkState(queryString.matches("(id~?=[0-9]+)?,?(name~?=[0-9a-zA-Z]+)?"));

	final List<ImmutablePair<String, ?>> tuplesList = Lists.newArrayList();
	final String[] tuples = queryString.split(SEPARATOR);
	for (final String tuple : tuples) {
	    final String[] keyAndValue = tuple.split(NEGATION + "?" + OP);
	    Preconditions.checkState(keyAndValue.length == 2);
	    tuplesList.add(constructTuple(keyAndValue[0], keyAndValue[1]));
	}

	return tuplesList;
    }

    private static ImmutablePair<String, ?> constructTuple(final String key, final String value) {
	if (key.endsWith(ID)) {
	    return new ImmutablePair<String, Long>(key, Long.parseLong(value));
	}
	return new ImmutablePair<String, String>(key, value);
    }

    public static Specification<Role> byId(final Long id, final boolean negated) {
        return new Specification<Role>() {
            @Override
            public final Predicate toPredicate(final Root<Role> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        	if (negated) {
        	    return cb.notEqual(root.get(Role_.id), id);
        	}
        	return cb.equal(root.get(Role_.id), id);
            }
        };
    }

    public static Specification<Role> byName(final String name, final boolean negated) {
        return new Specification<Role>() {
            @Override
            public final Predicate toPredicate(final Root<Role> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        	if (negated) {
        	    return cb.notEqual(root.get(Role_.name), name);
        	}
        	return cb.equal(root.get(Role_.name), name);
            }
        };
    }

    public static Specification<Role> resolveConstraint(final ImmutablePair<String, ?> constraint) {
        String constraintName = constraint.getLeft();
        boolean negated = false;
        if (constraintName.startsWith("~")) {
            negated = true;
            constraintName = constraintName.substring(1);
        }
    
        if (constraintName.equals("name")) {
            return byName((String) constraint.getRight(), negated);
        }
        if (constraintName.equals("id")) {
            return byId((Long) constraint.getRight(), negated);
        }
        return null;
    }

}
