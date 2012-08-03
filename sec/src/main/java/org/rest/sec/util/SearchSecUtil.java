package org.rest.sec.util;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.ClientOperation;
import org.rest.common.IEntity;
import org.rest.common.util.SearchCommonUtil;
import org.springframework.data.jpa.domain.Specification;

public final class SearchSecUtil {

    private SearchSecUtil() {
        throw new UnsupportedOperationException();
    }

    // util

    public static <T extends IEntity> Specification<T> resolveConstraint(final Triple<String, ClientOperation, String> constraint, final Class<T> clazz) {
        final String constraintName = constraint.getLeft();
        final boolean negated = isConstraintNegated(constraint);

        if (constraintName.equals(SearchCommonUtil.NAME)) {
            return QuerySpecifications.getByNameSpecification(clazz, constraint.getMiddle(), constraint.getRight(), negated);
        }
        if (constraintName.equals(SearchCommonUtil.ID)) {
            return QuerySpecifications.getByIdSpecification(clazz, Long.parseLong(constraint.getRight()), negated);
        }
        return null;
    }

    static boolean isConstraintNegated(final Triple<String, ClientOperation, String> constraint) {
        return constraint.getMiddle().isNegated();
    }

}
