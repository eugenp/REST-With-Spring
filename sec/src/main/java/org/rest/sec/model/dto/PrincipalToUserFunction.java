package org.rest.sec.model.dto;

import org.rest.sec.model.Principal;

import com.google.common.base.Function;

public final class PrincipalToUserFunction implements Function<Principal, User> {

    @Override
    public final User apply(final Principal principal) {
        return new User(principal);
    }

}
