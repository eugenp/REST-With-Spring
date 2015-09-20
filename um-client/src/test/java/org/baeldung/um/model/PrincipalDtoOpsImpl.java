package org.baeldung.um.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.client.IDtoOperations;
import org.baeldung.um.client.FixtureResourceFactory;
import org.baeldung.um.persistence.model.Principal;
import org.springframework.stereotype.Component;

@Component
public final class PrincipalDtoOpsImpl implements IDtoOperations<Principal> {

    public PrincipalDtoOpsImpl() {
        super();
    }

    // API

    // template method

    @Override
    public final Principal createNewResource() {
        return FixtureResourceFactory.createNewPrincipal();
    }

    @Override
    public final void invalidate(final Principal entity) {
        entity.setName(null);
    }

    @Override
    public final void change(final Principal resource) {
        resource.setName(randomAlphabetic(8));
    }

}
