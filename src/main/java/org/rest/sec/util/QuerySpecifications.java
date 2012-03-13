package org.rest.sec.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.rest.common.IEntity;
import org.rest.sec.model.Principal;
import org.rest.sec.model.Principal_;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Privilege_;
import org.rest.sec.model.Role;
import org.rest.sec.model.Role_;
import org.springframework.data.jpa.domain.Specification;

public final class QuerySpecifications {

    private QuerySpecifications() {
	throw new UnsupportedOperationException();
    }

    // user

    private static Specification<Principal> principalById(final Long id, final boolean negated) {
	return new Specification<Principal>() {
	    @Override
	    public final Predicate toPredicate(final Root<Principal> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
		if (negated) {
		    return cb.notEqual(root.get(Principal_.id), id);
		}
		return cb.equal(root.get(Principal_.id), id);
	    }
	};
    }

    private static Specification<Principal> principalByName(final String name, final boolean negated) {
	return new Specification<Principal>() {
	    @Override
	    public final Predicate toPredicate(final Root<Principal> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
		if (negated) {
		    return cb.notEqual(root.get(Principal_.name), name);
		}
		return cb.equal(root.get(Principal_.name), name);
	    }
	};
    }

    // role

    private static Specification<Role> roleById(final Long id, final boolean negated) {
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

    private static Specification<Role> roleByName(final String name, final boolean negated) {
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

    // privilege

    private static Specification<Privilege> privilegeById(final Long id, final boolean negated) {
	return new Specification<Privilege>() {
	    @Override
	    public final Predicate toPredicate(final Root<Privilege> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
		if (negated) {
		    return cb.notEqual(root.get(Privilege_.id), id);
		}
		return cb.equal(root.get(Privilege_.id), id);
	    }
	};
    }

    private static Specification<Privilege> privilegeByName(final String name, final boolean negated) {
	return new Specification<Privilege>() {
	    @Override
	    public final Predicate toPredicate(final Root<Privilege> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
		if (negated) {
		    return cb.notEqual(root.get(Privilege_.name), name);
		}
		return cb.equal(root.get(Privilege_.name), name);
	    }
	};
    }

    // API

    @SuppressWarnings("unchecked")
    public static <T extends IEntity> Specification<T> getByNameSpecification(final Class<T> clazz, final String name, final boolean negated) {
	if (clazz.equals(Role.class)) {
	    return (Specification<T>) roleByName(name, negated);
	}
	if (clazz.equals(Principal.class)) {
	    return (Specification<T>) principalByName(name, negated);
	}
	if (clazz.equals(Privilege.class)) {
	    return (Specification<T>) privilegeByName(name, negated);
	}

	return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends IEntity> Specification<T> getByIdSpecification(final Class<T> clazz, final Long id, final boolean negated) {
	if (clazz.equals(Role.class)) {
	    return (Specification<T>) roleById(id, negated);
	}
	if (clazz.equals(Principal.class)) {
	    return (Specification<T>) principalById(id, negated);
	}
	if (clazz.equals(Privilege.class)) {
	    return (Specification<T>) privilegeById(id, negated);
	}

	return null;
    }

}
