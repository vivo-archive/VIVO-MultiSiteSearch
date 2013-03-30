/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils.http;

/**
 * TODO
 */
public interface HttpWorker {
	/**
	 * Create a GET request object. Add parameters, headers, etc, then call
	 * execute() on the request.
	 */
	HttpWorkerRequest<String> get(String url) throws HttpWorkerException;

	/**
	 * Create a POST request object. Add parameters, headers, etc, then call
	 * execute() on the request.
	 */
	HttpWorkerRequest<String> post(String url) throws HttpWorkerException;

	/**
	 * The exception that these methods might throw.
	 */
	public static class HttpWorkerException extends Exception {
		public HttpWorkerException(String message, Throwable cause) {
			super(message, cause);
		}

		public HttpWorkerException(String message) {
			super(message);
		}

		public HttpWorkerException(Throwable cause) {
			super(cause);
		}
	}

}
