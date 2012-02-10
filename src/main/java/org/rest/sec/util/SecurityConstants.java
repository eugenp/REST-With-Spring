package org.rest.sec.util;

public final class SecurityConstants{
	
	/**
	 * Privileges <br/>
	 * - note: the fact that these Privileges are prefixed with `ROLE` is a Spring convention (which can be overriden if needed)
	 */
	public static final String PRIVILEGE_USER_WRITE = "ROLE_USER_WRITE";
	public static final String PRIVILEGE_ROLE_WRITE = "ROLE_ROLE_WRITE";
	
	public static final String ROLE_ADMIN = "AdminOfSecurityService";
	
	public static final String ADMIN_USERNAME = "eparaschiv";
	public static final String ADMIN_PASSWORD = "eparaschiv";
	
	private SecurityConstants(){
		throw new AssertionError();
	}
	
}
