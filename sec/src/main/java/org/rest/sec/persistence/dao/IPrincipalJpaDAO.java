package org.rest.sec.persistence.dao;

import org.rest.common.persistence.service.INameSupport;
import org.rest.sec.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrincipalJpaDAO extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, INameSupport<Principal> {
    //
}
