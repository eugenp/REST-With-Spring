package org.baeldung.um.persistence.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.baeldung.common.persistence.model.IEntity;
import org.baeldung.common.search.ClientOperation;
import org.baeldung.common.util.QueryConstants;
import org.baeldung.um.persistence.model.Principal;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.persistence.model.meta.Principal_;
import org.baeldung.um.persistence.model.meta.Privilege_;
import org.baeldung.um.persistence.model.meta.Role_;
import org.springframework.data.jpa.domain.Specification;

/**
 * The query specifications are built manually here; this is a limitation and should be contained to this only class
 */
@SuppressWarnings("unchecked")
public final class QuerySpecificationSec {

    private QuerySpecificationSec() {
        throw new UnsupportedOperationException();
    }

    // API - retrieve specifications for all OPs and all supported classes

    public static <T extends IEntity> Specification<T> getByNameSpecification(final Class<T> clazz, final ClientOperation op, final String name, final boolean negated) {
        switch (op) {
        case EQ:
            return getByNameSpecificationEq(clazz, name, negated);
        case NEG_EQ:
            return getByNameSpecificationEq(clazz, name, negated);

        case CONTAINS:
            return getByNameSpecificationContains(clazz, name, negated);
        case STARTS_WITH:
            return getByNameSpecificationStartsWith(clazz, name, negated);
        case ENDS_WITH:
            return getByNameSpecificationEndsWith(clazz, name, negated);

        case NEG_CONTAINS:
            return getByNameSpecificationContains(clazz, name, negated);
        case NEG_STARTS_WITH:
            return getByNameSpecificationStartsWith(clazz, name, negated);
        case NEG_ENDS_WITH:
            return getByNameSpecificationEndsWith(clazz, name, negated);

        default:
            break;
        }

        throw new UnsupportedOperationException("Received invalid operator: " + op);
    }

    // in between

    // id

    public static <T extends IEntity> Specification<T> getByIdSpecification(final Class<T> clazz, final Long id, final boolean negated) {
        if (clazz.equals(Role.class)) {
            return (Specification<T>) roleByIdEq(id, negated);
        }
        if (clazz.equals(Principal.class)) {
            return (Specification<T>) userByIdEq(id, negated);
        }
        if (clazz.equals(Privilege.class)) {
            return (Specification<T>) privilegeByIdEq(id, negated);
        }

        return null;
    }

    // name

    static <T extends IEntity> Specification<T> getByNameSpecificationEq(final Class<T> clazz, final String name, final boolean negated) {
        if (clazz.equals(Role.class)) {
            return (Specification<T>) roleByNameEq(name, negated);
        }
        if (clazz.equals(Principal.class)) {
            return (Specification<T>) userByNameEq(name, negated);
        }
        if (clazz.equals(Privilege.class)) {
            return (Specification<T>) privilegeByNameEq(name, negated);
        }

        return null;
    }

    static <T extends IEntity> Specification<T> getByNameSpecificationContains(final Class<T> clazz, final String name, final boolean negated) {
        if (clazz.equals(Role.class)) {
            return (Specification<T>) roleByNameContains(name, negated);
        }
        if (clazz.equals(Principal.class)) {
            return (Specification<T>) userByNameContains(name, negated);
        }
        if (clazz.equals(Privilege.class)) {
            return (Specification<T>) privilegeByNameContains(name, negated);
        }

        return null;
    }

    static <T extends IEntity> Specification<T> getByNameSpecificationStartsWith(final Class<T> clazz, final String name, final boolean negated) {
        if (clazz.equals(Role.class)) {
            return (Specification<T>) roleByNameStartsWith(name, negated);
        }
        if (clazz.equals(Principal.class)) {
            return (Specification<T>) userByNameStartsWith(name, negated);
        }
        if (clazz.equals(Privilege.class)) {
            return (Specification<T>) privilegeByNameStartsWith(name, negated);
        }

        return null;
    }

    static <T extends IEntity> Specification<T> getByNameSpecificationEndsWith(final Class<T> clazz, final String name, final boolean negated) {
        if (clazz.equals(Role.class)) {
            return (Specification<T>) roleByNameEndsWith(name, negated);
        }
        if (clazz.equals(Principal.class)) {
            return (Specification<T>) userByNameEndsWith(name, negated);
        }
        if (clazz.equals(Privilege.class)) {
            return (Specification<T>) privilegeByNameEndsWith(name, negated);
        }

        return null;
    }

    // individual specifications

    // user

    private static Specification<Principal> userByIdEq(final Long id, final boolean negated) {
        return new Specification<Principal>() {
            @Override
            public final Predicate toPredicate(final Root<Principal> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notEqual(root.get(Principal_.id), id);
                }
                return builder.equal(root.get(Principal_.id), id);
            }
        };
    }

    private static Specification<Principal> userByNameEq(final String name, final boolean negated) {
        return QuerySpecificationSec.<Principal> entityByKeyEq(name, negated, Principal_.name);
    }

    private static Specification<Principal> userByNameContains(final String name, final boolean negated) {
        return QuerySpecificationSec.<Principal> entityByKeyContains(name, negated, Principal_.name);
    }

    private static Specification<Principal> userByNameStartsWith(final String name, final boolean negated) {
        return QuerySpecificationSec.<Principal> entityByKeyStartsWith(name, negated, Principal_.name);
    }

    private static Specification<Principal> userByNameEndsWith(final String name, final boolean negated) {
        return QuerySpecificationSec.<Principal> entityByKeyEndsWith(name, negated, Principal_.name);
    }

    // role

    private static Specification<Role> roleByIdEq(final Long id, final boolean negated) {
        return new Specification<Role>() {
            @Override
            public final Predicate toPredicate(final Root<Role> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notEqual(root.get(Role_.id), id);
                }
                return builder.equal(root.get(Role_.id), id);
            }
        };
    }

    private static Specification<Role> roleByNameEq(final String name, final boolean negated) {
        return QuerySpecificationSec.<Role> entityByKeyEq(name, negated, Role_.name);
    }

    private static Specification<Role> roleByNameContains(final String name, final boolean negated) {
        return QuerySpecificationSec.<Role> entityByKeyContains(name, negated, Role_.name);
    }

    private static Specification<Role> roleByNameEndsWith(final String name, final boolean negated) {
        return QuerySpecificationSec.<Role> entityByKeyEndsWith(name, negated, Role_.name);
    }

    private static Specification<Role> roleByNameStartsWith(final String name, final boolean negated) {
        return QuerySpecificationSec.<Role> entityByKeyStartsWith(name, negated, Role_.name);
    }

    // privilege

    private static Specification<Privilege> privilegeByIdEq(final Long id, final boolean negated) {
        return new Specification<Privilege>() {
            @Override
            public final Predicate toPredicate(final Root<Privilege> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notEqual(root.get(Privilege_.id), id);
                }
                return builder.equal(root.get(Privilege_.id), id);
            }
        };
    }

    private static Specification<Privilege> privilegeByNameEq(final String name, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyEq(name, negated, Privilege_.name);
    }

    private static Specification<Privilege> privilegeByNameContains(final String name, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyContains(name, negated, Privilege_.name);
    }

    private static Specification<Privilege> privilegeByNameStartsWith(final String name, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyStartsWith(name, negated, Privilege_.name);
    }

    private static Specification<Privilege> privilegeByNameEndsWith(final String name, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyEndsWith(name, negated, Privilege_.name);
    }

    private static Specification<Privilege> privilegeByDescriptionEq(final String description, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyEq(description, negated, Privilege_.description);
    }

    private static Specification<Privilege> privilegeByDescriptionContains(final String description, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyContains(description, negated, Privilege_.description);
    }

    private static Specification<Privilege> privilegeByDescriptionStartsWith(final String description, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyStartsWith(description, negated, Privilege_.description);
    }

    private static Specification<Privilege> privilegeByDescriptionEndsWith(final String description, final boolean negated) {
        return QuerySpecificationSec.<Privilege> entityByKeyEndsWith(description, negated, Privilege_.description);
    }

    // generic

    private static <T extends IEntity> Specification<T> entityByKeyEndsWith(final String value, final boolean negated, final SingularAttribute<T, String> metaField) {
        return new Specification<T>() {
            @Override
            public final Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notLike(builder.lower(root.get(metaField)), QueryConstants.ANY_SERVER + value.toLowerCase());
                }
                return builder.like(builder.lower(root.get(metaField)), QueryConstants.ANY_SERVER + value.toLowerCase());
            }
        };
    }

    private static <T extends IEntity> Specification<T> entityByKeyStartsWith(final String value, final boolean negated, final SingularAttribute<T, String> metaField) {
        return new Specification<T>() {
            @Override
            public final Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notLike(builder.lower(root.get(metaField)), value.toLowerCase() + QueryConstants.ANY_SERVER);
                }
                return builder.like(builder.lower(root.get(metaField)), value.toLowerCase() + QueryConstants.ANY_SERVER);
            }
        };
    }

    private static <T extends IEntity> Specification<T> entityByKeyContains(final String value, final boolean negated, final SingularAttribute<T, String> metaField) {
        return new Specification<T>() {
            @Override
            public final Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notLike(builder.lower(root.get(metaField)), QueryConstants.ANY_SERVER + value.toLowerCase() + QueryConstants.ANY_SERVER);
                }
                return builder.like(builder.lower(root.get(metaField)), QueryConstants.ANY_SERVER + value.toLowerCase() + QueryConstants.ANY_SERVER);
            }
        };
    }

    private static <T extends IEntity> Specification<T> entityByKeyEq(final String value, final boolean negated, final SingularAttribute<T, String> metaField) {
        return new Specification<T>() {
            @Override
            public final Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
                if (negated) {
                    return builder.notEqual(builder.lower(root.get(metaField)), value.toLowerCase());
                }
                return builder.equal(builder.lower(root.get(metaField)), value.toLowerCase());
            }
        };
    }

}
