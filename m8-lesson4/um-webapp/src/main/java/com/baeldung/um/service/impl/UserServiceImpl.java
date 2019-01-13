package com.baeldung.um.service.impl;

import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.common.persistence.service.AbstractService;
import com.baeldung.common.security.SpringSecurityUtil;
import com.baeldung.um.persistence.dao.IUserJpaDao;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.service.IUserService;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    @Autowired
    IUserJpaDao dao;

    @Autowired
    @Qualifier("userCounter")
    private Counter userCounter;

    public UserServiceImpl() {
        super(User.class);
    }

    // API

    // find

    @Override
    @Transactional(readOnly = true)
    public User findByName(final String name) {
        logger.info("[user] increment findByName by 1, name = {}", name);
        userCounter.increment();
        return dao.findByName(name);
    }

    // other

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        final String userName = SpringSecurityUtil.getNameOfCurrentUser();
        return getDao().findByName(userName);
    }

    // Spring

    @Override
    protected final IUserJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<User> getSpecificationExecutor() {
        return dao;
    }

}
