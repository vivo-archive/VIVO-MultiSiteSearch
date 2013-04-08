/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.discovery;

import edu.cornell.mannlib.vivo.mss.MssException;

/**
 * Problem in the URI Discovery phase.
 */
public class DiscoveryWorkerException extends MssException {
	public DiscoveryWorkerException(String message, Throwable cause) {
		super(message, cause);
	}

	public DiscoveryWorkerException(String message) {
		super(message);
	}
}
