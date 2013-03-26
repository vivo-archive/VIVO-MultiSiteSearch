/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import java.util.Arrays;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.cornell.mannlib.vivo.mms.utils.XmlUtils.XmlUtilsException;

/**
 * TODO
 */
public class HttpWorkerImpl implements HttpWorker {
	private static final Log log = LogFactory.getLog(HttpWorkerImpl.class);

	@Override
	public String getRdfString(String url, Parameter... parameters)
			throws HttpWorkerException {
		log.debug("Request is: '" + url + "' " + Arrays.asList(parameters));
		HttpClient httpClient = new HttpClient();

		PostMethod method = new PostMethod(url);

		for (Parameter p : parameters) {
			method.addParameter(p.name, p.value);
		}

		// method.addRequestHeader(new Header("accept", "application/rdf+xml"));

		try {
			log.debug("About to get RDF/XML");
			int statusCode = httpClient.executeMethod(method);
			log.debug("HTTP status was " + statusCode);

			String response = method.getResponseBodyAsString(100_000_000);
			return response;
		} catch (Exception e) {
			throw new HttpWorkerException(e);
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public Document getRdfXml(String url, Parameter... parameters)
			throws HttpWorkerException {
		try {
			return XmlUtils.parseXml(getRdfString(url, parameters));
		} catch (XmlUtilsException e) {
			throw new HttpWorkerException(e);
		}
	}

	@Override
	public Model getRdfModel(String url, Parameter... parameters)
			throws HttpWorkerException {
		Model model = ModelFactory.createDefaultModel();
		model.read(getRdfString(url, parameters));
		return model;
	}

}
