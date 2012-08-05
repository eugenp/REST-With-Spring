package org.rest.sec.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.common.client.IEntityOperations;
import org.rest.sec.model.Privilege;
import org.springframework.stereotype.Component;

@Component
public final class PrivilegeEntityOpsImpl implements IEntityOperations<Privilege> {

    public PrivilegeEntityOpsImpl() {
        super();
    }

    // template method

    @Override
    public final Privilege createNewEntity() {
        return new Privilege(randomAlphabetic(8));
    }

    @Override
    public final void invalidate(final Privilege entity) {
        entity.setName(null);
    }

    @Override
    public void change(final Privilege resource) {
        resource.setName(randomAlphabetic(8));
    }

}
