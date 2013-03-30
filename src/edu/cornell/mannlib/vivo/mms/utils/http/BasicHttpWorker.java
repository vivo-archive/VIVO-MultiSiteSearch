/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils.http;

import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import edu.cornell.mannlib.vivo.mms.utils.http.BasicHttpWorkerRequest.StringHttpWorkerRequest;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest.Method;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest.Parameter;

/**
 * The basic implementation of the HttpClient.
 * 
 * Obtain a request object from get(String) or post(String). Use the methods on
 * HttpWorkerRequest to populate the request.
 * 
 * The request object keeps a reference to the HttpWorker that created it, and
 * when you call execute on the request, it will call back to its parent.
 */
public class BasicHttpWorker implements HttpWorker {
	private final HttpClient httpClient = new HttpClient();

	// TODO Refactor to accept an HttpClient in the constructor -- add timeout
	// to client.

	@Override
	public HttpWorkerRequest<String> get(String url) throws HttpWorkerException {
		return new StringHttpWorkerRequest(this, url, Method.GET);
	}

	@Override
	public HttpWorkerRequest<String> post(String url)
			throws HttpWorkerException {
		return new StringHttpWorkerRequest(this, url, Method.POST);
	}

	/**
	 * The request calls this method when it is ready for execution.
	 */
	protected String executeRequest(BasicHttpWorkerRequest<?> request)
			throws HttpWorkerException {
		HttpMethod method = (request.getMethod() == Method.GET) ? buildGetMethod(request)
				: buildPostMethod(request);

		try {
			httpClient.executeMethod(method);
			return IOUtils.toString(method.getResponseBodyAsStream(), "UTF-8");
		} catch (Exception e) {
			throw new HttpWorkerException(e);
		} finally {
			method.releaseConnection();
		}
	}

	private HttpMethod buildPostMethod(BasicHttpWorkerRequest<?> request) {
		PostMethod method = new PostMethod(request.getUrlWithoutParameters());
		method.addParameters(parametersAsPairs(request.getParameters()));
		applyAcceptTypes(method, request);
		return method;
	}

	private HttpMethod buildGetMethod(BasicHttpWorkerRequest<?> request) {
		GetMethod method = new GetMethod(request.getUrlWithoutParameters());
		method.setQueryString(parametersAsPairs(request.getParameters()));
		applyAcceptTypes(method, request);
		return method;
	}

	private NameValuePair[] parametersAsPairs(List<Parameter> parameters) {
		NameValuePair[] pairs = new NameValuePair[parameters.size()];
		for (int i = 0; i < pairs.length; i++) {
			Parameter p = parameters.get(i);
			pairs[i] = new NameValuePair(p.name, p.value);
		}
		return pairs;
	}

	private void applyAcceptTypes(HttpMethod method,
			BasicHttpWorkerRequest<?> request) {
		List<String> acceptTypes = request.getAcceptTypes();
		if (!acceptTypes.isEmpty()) {
			String acceptable = StringUtils.join(acceptTypes, ", ");
			method.addRequestHeader(new Header("Accept", acceptable));
		}
	}

}
