package com.baeldung.um.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

    public static volatile SingularAttribute<Role, Long> id;
    public static volatile SingularAttribute<Role, String> name;
    public static volatile SetAttribute<Role, Privilege> privileges;

}
