package org.baeldung.rest.model.dto;

import org.baeldung.rest.model.Principal;
import org.baeldung.rest.model.User;

import com.google.common.base.Function;

public final class PrincipalToUserFunction implements Function<Principal, User> {

    @Override
    public final User apply(final Principal principal) {
        return new User(principal);
    }

}
