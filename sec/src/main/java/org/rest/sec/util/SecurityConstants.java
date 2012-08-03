package org.rest.sec.util;

public final class SecurityConstants {

    /**
     * Privileges <br/>
     * - note: the fact that these Privileges are prefixed with `ROLE` is a Spring convention (which can be overriden if needed)
     */
    public static final String CAN_USER_WRITE = "ROLE_USER_WRITE";
    public static final String CAN_ROLE_WRITE = "ROLE_ROLE_WRITE";

    public static final String ROLE_ADMIN = "AdminOfSecurityService";

    public static final String ADMIN_USERNAME = "eparaschiv";
    public static final String ADMIN_PASSWORD = "eparaschiv";
    public static final String ADMIN_EMAIL = "eparaschiv@gmail.com";

    public static final String NAME = ADMIN_USERNAME;
    public static final String PASS = ADMIN_PASSWORD;
    public static final String EMAIL = ADMIN_EMAIL;

    private SecurityConstants() {
        throw new AssertionError();
    }

}
