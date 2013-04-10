/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr;

/**
 * A problem occured with the DocumentMaker.
 */
public class DocumentMakerException extends Exception {
	public DocumentMakerException(String message) {
		super(message);
	}

	public DocumentMakerException(String message, Throwable cause) {
		super(message, cause);
	}
}
