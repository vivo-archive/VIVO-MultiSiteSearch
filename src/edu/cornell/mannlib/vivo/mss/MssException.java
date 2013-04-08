/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss;

/**
 * Indicates a problem in the MultiSite Search Indexer.
 * 
 * This should be the parent class for any other Exception classes define in the
 * application.
 * 
 * We want to add context at every level, so we don't implement any constructors
 * that don't provide a message.
 */
public class MssException extends Exception {
	public MssException(String message, Throwable cause) {
		super(message, cause);
	}

	public MssException(String message) {
		super(message);
	}

}
