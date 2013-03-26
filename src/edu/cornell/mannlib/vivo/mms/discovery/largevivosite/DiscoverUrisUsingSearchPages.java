/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.largevivosite;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.common.collect.Iterables;

import edu.cornell.mannlib.vivo.mms.discovery.AbstractDiscoveryWorkerHarness;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker.HttpWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker.Parameter;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper;

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
		private int nextPageStart;
		private final List<Iterable<String>> results = new ArrayList<>();
		private boolean done;

		public Worker(String siteUrl, String classUri,
				DiscoverUrisContext duContext) {
			super(siteUrl, classUri, duContext);
		}

		@Override
		public Iterable<String> discover() throws HttpWorkerException {
			while (!done) {
				fetchAndParsePage();
			}
			return Iterables.concat(results);
		}

		/**
		 * Fetch a page of search results. Add them to the collected results. If
		 * there are subsequent pages, record the start index. Otherwise, we are
		 * done.
		 */
		private void fetchAndParsePage() throws HttpWorkerException {
			HttpWorker http = duContext.getHttpWorker();
			Document resultDoc = http.getRdfXml(siteUrl + "/search",
					new Parameter("querytext", "type:" + classUri),
					new Parameter("startIndex", nextPageStart), new Parameter(
							"hitsPerPage", HITS_PER_PAGE), new Parameter("xml",
							1));

			XPathHelper xp = XPathHelper.getHelper();
			Node topNode = resultDoc.getDocumentElement();
			List<Node> validityTest = xp.findNodes(
					"/response/lst[@name='responseHeader']", topNode);
			if (validityTest.isEmpty()) {
				throw new Error(
						"Can't continue: response to search request was not in the expected format.");
			}

			List<Node> nextPageLinks = xp
					.findNodes(
							"/response/lst[@name='responseHeader']/str[@name='nextPage']",
							topNode);
			if (nextPageLinks.isEmpty()) {
				done = true;
			} else {
				nextPageStart = nextPageStart + HITS_PER_PAGE;
			}

			List<Node> responseResults = xp.findNodes(
					"/response/result[@name='response']", topNode);
			if (responseResults.isEmpty()) {
				log.error("No results node!!!");
				return;
			}

			Node resultNode = responseResults.get(0);
			List<Node> uriNodes = xp
					.findNodes("//str[@name='uri']", resultNode);

			List<String> uris = new ArrayList<>();
			for (Node uriNode: uriNodes) {
				uris.add(uriNode.getTextContent());
			}
			
			results.add(uris);
		}
	}
}
