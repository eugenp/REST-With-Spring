package org.rest.sec.persistence.dao;

import org.rest.sec.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrincipalJpaDAO extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal> {

    Principal findByName(final String name);

}
