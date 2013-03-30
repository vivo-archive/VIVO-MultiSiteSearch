/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.largevivosite;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.cornell.mannlib.vivo.mms.discovery.AbstractDiscoveryWorkerHarness;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper.XpathHelperException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker.HttpWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest.Accept;

/**
 * Issue repeated requests to search for the individual URIs until we have all
 * of them.
 */
public class DiscoverUrisUsingSearchPages extends
		AbstractDiscoveryWorkerHarness {
	private static final Log log = LogFactory
			.getLog(DiscoverUrisUsingSearchPages.class);

	private static final int HITS_PER_PAGE = 10000;

	@Override
	protected DiscoveryWorker getWorker(String siteUrl, String classUri,
			DiscoverUrisContext duContext) {
		return new Worker(siteUrl, classUri, duContext);
	}

	private static class Worker extends DiscoveryWorker {
		private final List<String> results = new ArrayList<>();
		private final XPathHelper xp = XPathHelper.getHelper();

		public Worker(String siteUrl, String classUri,
				DiscoverUrisContext duContext) {
			super(siteUrl, classUri, duContext);
		}

		@Override
		public Iterable<String> discover() throws DiscoveryWorkerException {
			String url = siteUrl + "/search";
			try {
				url = processPage(fetchPage(firstRequest(url)));
				while (url != null) {
					url = processPage(fetchPage(nextRequest(url)));
				}
				return results;
			} catch (HttpWorkerException | XpathHelperException e) {
				throw new DiscoveryWorkerException(
						"Failed to fetch the page at '" + url + "'", e);
			}
		}

		private HttpWorkerRequest<String> firstRequest(String url)
				throws HttpWorkerException {
			return duContext.getHttpWorker().get(url)
					.parameter("querytext", "type:" + classUri)
					.parameter("hitsPerPage", HITS_PER_PAGE)
					.parameter("xml", 1);
		}

		private HttpWorkerRequest<String> nextRequest(String url)
				throws HttpWorkerException {
			return duContext.getHttpWorker().get(url);
		}

		private Document fetchPage(HttpWorkerRequest<String> request)
				throws HttpWorkerException {
			return request.accept(Accept.RDF_XML).asXML().execute();
		}

		/**
		 * Parse the page and add the URIs to the collected results. Return the
		 * URL of the next page of results, or null if we are done.
		 */
		private String processPage(Document pageDoc)
				throws XpathHelperException {
			Node topNode = pageDoc.getDocumentElement();
			Node responseHeaderNode = xp.findFirstNode(
					"/response/lst[@name='responseHeader']", topNode);
			if (responseHeaderNode == null) {
				throw new XpathHelperException("Response to "
						+ "search request was not in the expected format: "
						+ "couldn't find the <responseHeader> node.");
			}

			String pattern2 = "/response/result[@name='response']//str[@name='uri']";
			for (Node uriNode : xp.findNodes(pattern2, topNode)) {
				results.add(uriNode.getTextContent());
			}

			String pattern = "str[@name='nextPage']";
			Node nextPageNode = xp.findFirstNode(pattern, responseHeaderNode);
			if (nextPageNode == null) {
				return null;
			} else if (results.size() > 300) { // TODO KLUGE to limit test time
				return null;
			} else {
				String relativeUrl = nextPageNode.getTextContent();
				try {
					return new URL(new URL(siteUrl), relativeUrl)
							.toExternalForm();
				} catch (MalformedURLException e) {
					throw new XpathHelperException("Couldn't convert the "
							+ "next page URL from relative to absolute: "
							+ "next page URL '" + relativeUrl + "', site URL '"
							+ siteUrl + "'");
				}
			}
		}

	}
}
