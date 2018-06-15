package com.baeldung.um.persistence.dao;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.um.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrivilegeJpaDao extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege>, IByNameApi<Privilege> {
    //
}
