package org.rest.sec.security;

import java.util.Collection;
import java.util.Set;

import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

public final class SecurityConvertionUtil {

    private SecurityConvertionUtil() {
        throw new AssertionError();
    }

    // API

    public static Set<Privilege> convertRolesToPrivileges(final Iterable<Role> roles) {
        final Set<Privilege> privileges = Sets.<Privilege> newHashSet();
        for (final Role roleOfUser : roles) {
            privileges.addAll(roleOfUser.getPrivileges());
        }
        return privileges;
    }

    public static Collection<String> convertPrivilegesToPrivilegeNames(final Collection<Privilege> privileges) {
        final Function<Object, String> toStringFunction = Functions.toStringFunction();
        return Collections2.transform(privileges, toStringFunction);
    }

    public static Collection<String> convertRolesToPrivilegeNames(final Iterable<Role> roles) {
        final Set<Privilege> privileges = convertRolesToPrivileges(roles);
        return convertPrivilegesToPrivilegeNames(privileges);
    }

}
