/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.vivo141site;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.cornell.mannlib.vivo.mms.discovery.AbstractDiscoveryWorkerHarness;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker.HttpWorkerException;

/**
 * Issue repeated requests to search for the individual URIs until we have all
 * of them.
 */
public class DiscoverUrisUsingIndividualList extends
		AbstractDiscoveryWorkerHarness {
	private static final Log log = LogFactory
			.getLog(DiscoverUrisUsingIndividualList.class);

	// private static final int HITS_PER_PAGE = 10000;

	@Override
	protected DiscoveryWorker getWorker(String siteUrl, String classUri,
			DiscoverUrisContext duContext) {
		return new Worker(siteUrl, classUri, duContext);
	}

	private static class Worker extends DiscoveryWorker {
		private int pageIndex;
		private String nextPageUrl;
		private List<String> result = new ArrayList<>();
		private boolean done;

		public Worker(String siteUrl, String classUri,
				DiscoverUrisContext duContext) {
			super(siteUrl, classUri, duContext);
		}

		@Override
		public Iterable<String> discover() throws DiscoveryWorkerException {
			figureNextPageUrl();

			while (!done) {
				fetchAndParsePage();
			}
			return result;
		}

		private void figureNextPageUrl() {
			try {
				pageIndex++;
				String encodedClassUri = URLEncoder.encode(classUri, "UTF-8");
				nextPageUrl = siteUrl + "/individuallist?" + "page="
						+ pageIndex + "&vclassId=" + encodedClassUri;
				log.debug("Next page URL: '" + nextPageUrl + "'");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Really? We don't support UTF-8?", e);
			}
		}

		/**
		 * Fetch a page of search results. Add them to the collected results.
		 * When we get a page with no results, we are done.
		 */
		private void fetchAndParsePage() throws DiscoveryWorkerException {
			URL urlForSite;
			try {
				urlForSite = new URL(siteUrl);

				Document pageDoc = duContext.getHttpWorker().get(nextPageUrl)
						.asHtml().execute();

				List<String> uris = new ArrayList<>();
				Elements links = pageDoc
						.select("a[href][title=individual name]");
				log.debug("Got this many URIs: " + links.size());
				for (Element link : links) {
					String href = link.attr("href");
					try {
						uris.add(new URL(urlForSite, href).toExternalForm());
					} catch (Exception e) {
						throw new DiscoveryWorkerException(
								"Cannot make a valid URL from '" + href
										+ "', relative to '" + urlForSite + "'",
								e);
					}
				}

				if (uris.isEmpty()) {
					done = true;
				} else {
					result.addAll(uris);
					figureNextPageUrl();
				}

				// KLUGE
				if (pageIndex > 10) {
					done = true;
				}
			} catch (MalformedURLException e) {
				throw new DiscoveryWorkerException("Site URL is not valid ", e);
			} catch (HttpWorkerException e) {
				throw new DiscoveryWorkerException("Problem fetching HTML at '"
						+ nextPageUrl + "'", e);
			} catch (Exception e) {
				throw new DiscoveryWorkerException("Failed to fetch and parse "
						+ "HTML pageL at '" + nextPageUrl + "'", e);
			}
		}
	}
}
