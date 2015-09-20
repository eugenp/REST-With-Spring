package org.baeldung.um.persistence.dao;

import org.baeldung.common.interfaces.IByNameApi;
import org.baeldung.um.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleJpaDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, IByNameApi<Role> {
    //
}
