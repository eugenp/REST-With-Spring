package org.rest.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SpringSecurityUtil {

    private SpringSecurityUtil() {
        throw new AssertionError();
    }

    // API

    /**
     * Gets the current user details.
     * 
     * @return the current user details or null if can't be retrieved.
     */
    static UserDetails getCurrentUserDetails() {
        final Authentication authentication = getCurrentAuthentication();
        if (authentication == null) {
            return null;
        }

        final Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }

        return null;
    }

    public static Authentication getCurrentAuthentication() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }
        return securityContext.getAuthentication();
    }

    public static String getNameOfCurrentPrincipal() {
        final Authentication authentication = getCurrentAuthentication();
        if (authentication == null) {
            return null;
        }

        return authentication.getName();
    }

}
