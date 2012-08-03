package org.rest.persistence.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.rest.sec.model.Privilege;
import org.springframework.data.jpa.domain.Specification;

public final class PrivilegeSpecifications {

    private PrivilegeSpecifications() {
        throw new AssertionError();
    }

    // API

    public static Specification<Privilege> lastNameIsLike(final String searchTerm) {
        return new Specification<Privilege>() {
            @Override
            public final Predicate toPredicate(final Root<Privilege> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                // return builder.equal( root.get, y );
                return null;
            }
        };
    }

}
