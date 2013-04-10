/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr;

/**
 * A problem occured with the SolrIndexService.
 */
public class SolrIndexServiceException extends Exception {
	public SolrIndexServiceException(String message) {
		super(message);
	}

	public SolrIndexServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
