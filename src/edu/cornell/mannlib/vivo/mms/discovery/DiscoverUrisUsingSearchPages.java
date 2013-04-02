/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.cornell.mannlib.vivo.mms.discovery.BaseDiscoveryWorker;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper.XpathHelperException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest.Accept;

/**
 * Issue repeated requests to search for the individual URIs until we have all
 * of them.
 * 
 * Doesn't work on VIVO 1.4 or 1.4.1
 */
public class DiscoverUrisUsingSearchPages extends BaseDiscoveryWorker {
	private static final int HITS_PER_PAGE = 10000;

	private final HttpWorker http;

	public DiscoverUrisUsingSearchPages(Iterable<String> classUris,
			HttpWorker http) {
		super(classUris);
		this.http = http;
	}

	@Override
	protected Iterable<String> getUrisForClassAtSite(String siteUrl,
			String classUri) throws DiscoveryWorkerException {
		List<String> results = new ArrayList<>();

		String url = siteUrl + "/search";
		try {
			Document page = fetchPage(firstRequest(url, classUri));
			url = processPage(page, results, siteUrl);

			while (url != null) {
				page = fetchPage(nextRequest(url));
				url = processPage(page, results, siteUrl);
			}

			return results;
		} catch (Exception e) {
			throw new DiscoveryWorkerException("Failed to fetch the page at '"
					+ url + "'", e);
		}
	}

	private HttpWorkerRequest<String> firstRequest(String url, String classUri)
			throws HttpWorkerException {
		return http.get(url).parameter("querytext", "type:" + classUri)
				.parameter("hitsPerPage", HITS_PER_PAGE).parameter("xml", 1);
	}

	private HttpWorkerRequest<String> nextRequest(String url)
			throws HttpWorkerException {
		return http.get(url);
	}

	private Document fetchPage(HttpWorkerRequest<String> request)
			throws HttpWorkerException {
		return request.accept(Accept.RDF_XML).asXML().execute();
	}

	/**
	 * Parse the page and add the URIs to the collected results. Return the URL
	 * of the next page of results, or null if we are done.
	 */
	private String processPage(Document pageDoc, List<String> results,
			String siteUrl) throws XpathHelperException {
		XPathHelper xp = XPathHelper.getHelper();
		Node topNode = pageDoc.getDocumentElement();

		Node responseHeaderNode = findResponseHeaderNode(xp, topNode);
		results.addAll(findUris(xp, topNode));
		return findNextPageUrl(siteUrl, xp, responseHeaderNode);
	}

	private Node findResponseHeaderNode(XPathHelper xp, Node topNode)
			throws XpathHelperException {
		Node responseHeaderNode = xp.findFirstNode(
				"/response/lst[@name='responseHeader']", topNode);
		if (responseHeaderNode == null) {
			throw new XpathHelperException("Response to "
					+ "search request was not in the expected format: "
					+ "couldn't find the <responseHeader> node.");
		}
		return responseHeaderNode;
	}

	private Collection<String> findUris(XPathHelper xp, Node topNode)
			throws XpathHelperException {
		List<String> uris = new ArrayList<>();
		String pattern2 = "/response/result[@name='response']//str[@name='uri']";
		for (Node uriNode : xp.findNodes(pattern2, topNode)) {
			uris.add(uriNode.getTextContent());
		}
		return uris;
	}

	private String findNextPageUrl(String siteUrl, XPathHelper xp,
			Node responseHeaderNode) throws XpathHelperException {
		String pattern = "str[@name='nextPage']";
		Node nextPageNode = xp.findFirstNode(pattern, responseHeaderNode);
		if (nextPageNode == null) {
			return null;
		} else {
			String relativeUrl = nextPageNode.getTextContent();
			try {
				return new URL(new URL(siteUrl), relativeUrl).toExternalForm();
			} catch (Exception e) {
				throw new XpathHelperException("Couldn't convert the "
						+ "next page URL from relative to absolute: "
						+ "next page URL '" + relativeUrl + "', site URL '"
						+ siteUrl + "'");
			}
		}
	}
}
