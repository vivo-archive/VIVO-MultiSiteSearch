/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss;

/**
 * A general exception that we can throw from Mapper.map()
 */
public class MssMapperFailedException extends RuntimeException {
	public MssMapperFailedException(String message) {
		super(message);
	}

	public MssMapperFailedException(Throwable cause) {
		super(cause);
	}

	public MssMapperFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
