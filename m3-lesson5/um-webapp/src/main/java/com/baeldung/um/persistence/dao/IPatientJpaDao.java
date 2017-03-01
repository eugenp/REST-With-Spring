package com.baeldung.um.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.um.persistence.model.Patient;

public interface IPatientJpaDao extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient>, IByNameApi<Patient> {
    //
}
