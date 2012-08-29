package org.rest.common.security;

import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.ImmutableSet;

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
    public static UserDetails getCurrentUserDetails() {
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

    // is?

    /**
     * Check if current user is authenticated.
     * 
     * @return true if user is authenticated.
     */
    public static boolean isAuthenticated() {
        return SpringSecurityUtil.getCurrentUserDetails() != null;
    }

    /**
     * Check if current user is anonymous guest.
     * 
     * @return true if user is anonymous guest.
     */
    public static boolean isAnonymous() {
        return SpringSecurityUtil.getCurrentUserDetails() == null;
    }

    // has?

    /**
     * Check if current user has specified role.
     * 
     * @param role
     *            the role to check if user has.
     * @return true if user has specified role, otherwise false.
     */
    public static boolean hasRole(final String role) {
        final UserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails != null) {
            for (final GrantedAuthority each : userDetails.getAuthorities()) {
                if (each.getAuthority().equals(role)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if current user has any role of specified.
     * 
     * @param roles
     *            the array of roles.
     * @return true if has any role, otherwise false.
     */
    public static boolean hasAnyRole(final String... roles) {
        final UserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails != null) {
            final Set<String> rolesSet = ImmutableSet.copyOf(roles);
            for (final GrantedAuthority each : userDetails.getAuthorities()) {
                if (rolesSet.contains(each.getAuthority())) {
                    return true;
                }
            }
        }

        return false;
    }

    // auth

    /**
     * Calculates an authorization key for user.
     * 
     * @param userDetails
     *            the user details.
     * @return the calculated authorization key.
     */
    public static String calculateAuthorizationKey(final UserDetails userDetails) {
        return calculateAuthorizationKey(userDetails.getUsername(), userDetails.getPassword());
    }

    public static String calculateAuthorizationKey(final String username, final String password) {
        final String authorizationString = username + ":" + password;
        return new String(Base64.encodeBase64(authorizationString.getBytes()));
    }

}
