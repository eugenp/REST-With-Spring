package org.baeldung.um.web.dto;

import org.baeldung.um.persistence.model.Principal;
import org.baeldung.um.persistence.model.User;

import com.google.common.base.Function;

public final class PrincipalToUserFunction implements Function<Principal, User> {

    @Override
    public final User apply(final Principal principal) {
        return new User(principal);
    }

}
