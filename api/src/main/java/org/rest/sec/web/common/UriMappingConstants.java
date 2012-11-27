package org.rest.sec.web.common;

public final class UriMappingConstants {

    public static final String USERS = "users";
    public static final String PRIVILEGES = "privileges";
    public static final String ROLES = "roles";

    // discoverability

    public static final class Singural {

        public static final String USER = "user";
        public static final String PRIVILEGE = "privilege";
        public static final String ROLE = "role";

    }

    public static final String AUTHENTICATION = "authentication";

    private UriMappingConstants() {
        throw new AssertionError();
    }

    // API

}
