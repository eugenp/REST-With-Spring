package com.baeldung.common.spring.util;

public final class Profiles {

    public static final String DEPLOYED = "deployed";
    public static final String PRODUCTION = "production";

    private Profiles() {
        throw new AssertionError();
    }

}
