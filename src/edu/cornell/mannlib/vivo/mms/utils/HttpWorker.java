/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import org.w3c.dom.Document;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * TODO
 */
public interface HttpWorker {
	/**
	 * Name/value pairs to be added to a request.
	 */
	public static class Parameter {
		final String name;
		final String value;

		public Parameter(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public Parameter(String name, Object value) {
			this.name = name;
			this.value = String.valueOf(value);
		}

		@Override
		public String toString() {
			return "Parameter[" + name + "=" + value + "]";
		}
	}

	/**
	 * Issue a request for RDF/XML and parse the result as a String.
	 */
	String getRdfString(String url, Parameter... parameters)
			throws HttpWorkerException;

	/**
	 * Issue a request for RDF/XML and parse the result as an XML document.
	 */
	Document getRdfXml(String url, Parameter... parameters)
			throws HttpWorkerException;

	/**
	 * Issue a request for RDF/XML and parse the result as a Jena Model.
	 */
	Model getRdfModel(String url, Parameter... parameters)
			throws HttpWorkerException;

	/**
	 * The exception that these methods might throw.
	 */
	public static class HttpWorkerException extends Exception {
		public HttpWorkerException(String message, Throwable cause) {
			super(message, cause);
		}

		public HttpWorkerException(Throwable cause) {
			super(cause);
		}
	}
}
