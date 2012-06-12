package org.rest.common.util;

import org.rest.util.SearchCommonUtil;

public final class QueryUtil{
	
	public static final String ID = "id";
	public static final String ID_NEG = ID + SearchCommonUtil.NEGATION;
	public static final String NAME = "name";
	public static final String NAME_NEG = NAME + SearchCommonUtil.NEGATION;
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_NAME_NEG = LOGIN_NAME + SearchCommonUtil.NEGATION;
	
	public static final String PAGE = "page";
	public static final String SIZE = "size";
	public static final String QUESTIONMARK = "?";
	
	private QueryUtil(){
		throw new AssertionError();
	}
	
	//
	
}
