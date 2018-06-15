package com.baeldung.um.util;

import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class UmConvertionUtil {

    private UmConvertionUtil() {
        throw new AssertionError();
    }

    // API

    public static Set<Privilege> convertRolesToPrivileges(final Collection<Role> roles) {
        return roles.stream().flatMap(role -> role.getPrivileges().stream()).collect(Collectors.toSet());
    }

    public static Collection<String> convertPrivilegesToPrivilegeNames(final Collection<Privilege> privileges) {
        return privileges.stream().map(Privilege::toString).collect(Collectors.toList());
    }

    public static Collection<String> convertRolesToPrivilegeNames(final Collection<Role> roles) {
        final Set<Privilege> privileges = convertRolesToPrivileges(roles);
        return convertPrivilegesToPrivilegeNames(privileges);
    }
}
