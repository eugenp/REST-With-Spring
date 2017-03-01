package com.baeldung.um.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.common.persistence.service.AbstractService;
import com.baeldung.um.persistence.dao.IPatientJpaDao;
import com.baeldung.um.persistence.model.Patient;
import com.baeldung.um.service.IPatientService;

@Service
@Transactional
public class PatientServiceImpl extends AbstractService<Patient> implements IPatientService {

    @Autowired
    private IPatientJpaDao dao;

    public PatientServiceImpl() {
        super();
    }

    // API

    // find

    @Override
    @Transactional(readOnly = true)
    public Patient findByName(final String name) {
        return dao.findByName(name);
    }

    // Spring

    @Override
    protected final IPatientJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Patient> getSpecificationExecutor() {
        return dao;
    }

}
