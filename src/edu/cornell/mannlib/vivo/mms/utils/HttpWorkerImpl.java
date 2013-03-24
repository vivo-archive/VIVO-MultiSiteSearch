/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import edu.cornell.mannlib.vivo.mms.utils.XmlUtils.XmlUtilsException;

/**
 * TODO
 */
public class HttpWorkerImpl implements HttpWorker {
	private static final Log log = LogFactory.getLog(HttpWorkerImpl.class);

	private static DocumentBuilderFactory factory = createDocBuilderFactory();

	private static DocumentBuilderFactory createDocBuilderFactory() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); // never forget this!
		return dbf;
	}

	/**
	 * Don't forget to URLEncode the parameter keys and values.
	 * 
	 * @throws XmlUtilsException
	 * 
	 * @see edu.cornell.mannlib.vivo.mms.utils.HttpWorker#getRdfXml(java.lang.String,
	 *      edu.cornell.mannlib.vivo.mms.utils.HttpWorker.Parameter[])
	 */
	@Override
	public Document getRdfXml(String url, Parameter... parameters)
			throws HttpWorkerException {
		HttpClient httpClient = new HttpClient();

		PostMethod method = new PostMethod(url);
		
		for (Parameter p : parameters) {
			method.addParameter(p.name, p.value);
		}
		
		method.addRequestHeader(new Header("accept", "application/rdf+xml"));
		
		try {
			log.debug("About to get RDF/XML");
			int statusCode = httpClient.executeMethod(method);
			log.debug("HTTP status was " + statusCode);

			String rdf = method.getResponseBodyAsString(100_000_000);

			return XmlUtils.parseXml(rdf);
		} catch (Exception e) {
			throw new HttpWorkerException(e);
		} finally {
			method.releaseConnection();
		}
	}
}
