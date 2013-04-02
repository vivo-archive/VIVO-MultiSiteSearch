/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils.http;

import org.apache.commons.httpclient.HttpClient;

/**
 * Create an HttpClient with the favored settings.
 */
public class HttpClientFactory {
	
	public static HttpClient standardClient() {
		HttpClient http = new HttpClient();
		
		// Set the socket timeout to 30 seconds.
		http.getParams().setSoTimeout(30000);
		return http;
	}

	private HttpClientFactory() {
		// nothing to instantiate.
	}
}
