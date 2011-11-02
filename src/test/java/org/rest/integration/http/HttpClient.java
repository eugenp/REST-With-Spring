package org.rest.integration.http;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpConnectionParams;

public final class HttpClient extends DefaultHttpClient{
	
	public static final int TIMEOUT = 2000; // ms
	
	public HttpClient(){
		super( new SingleClientConnManager() );
		
		HttpConnectionParams.setConnectionTimeout( this.getParams(), TIMEOUT );
		HttpConnectionParams.setSoTimeout( this.getParams(), TIMEOUT );
	}
	
}
