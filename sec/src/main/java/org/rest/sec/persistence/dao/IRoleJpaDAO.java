package org.rest.sec.persistence.dao;

import org.rest.common.persistence.service.INameSupport;
import org.rest.sec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleJpaDAO extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, INameSupport<Role> {
    //
}
