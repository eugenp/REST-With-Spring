package org.rest.util;

import java.util.Random;

public final class IdUtil{
	
	private IdUtil(){
		throw new AssertionError();
	}
	
	//
	
	public final static long randomPositiveLong(){
		long id = new Random().nextLong() * 10000;
		id = ( id < 0 ) ? ( -1 * id ) : id;
		return id;
	}
	public final static long randomNegativeLong(){
		long id = new Random().nextLong() * 10000;
		id = ( id > 0 ) ? ( -1 * id ) : id;
		return id;
	}
	
}
