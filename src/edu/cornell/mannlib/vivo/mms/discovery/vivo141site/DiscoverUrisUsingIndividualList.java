/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.vivo141site;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.cornell.mannlib.vivo.mms.discovery.BaseDiscoveryWorker;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerRequest;

/**
 * Issue repeated requests to search for the individual URIs until we have all
 * of them.
 */
public class DiscoverUrisUsingIndividualList extends BaseDiscoveryWorker {

	private final HttpWorker http;

	public DiscoverUrisUsingIndividualList(Iterable<String> classUris,
			HttpWorker http) {
		super(classUris);
		this.http = http;
	}

	@Override
	protected Iterable<String> getUrisForClassAtSite(String siteUrl,
			String classUri) throws DiscoveryWorkerException {
		try {
			int pageIndex = 0;
			List<String> result = new ArrayList<>();
			boolean done = false;

			while (!done) {
				HttpWorkerRequest<Document> pageRequest = figureNextPageRequest(
						siteUrl, classUri, ++pageIndex);
				done = fetchAndParsePage(new URL(siteUrl), pageRequest, result);
			}
			if (result.isEmpty()) {
				throw new DiscoveryWorkerException("Individual list for site '"
						+ siteUrl + "' and class '" + classUri
						+ "' returned no results.");
			}
			return result;
		} catch (MalformedURLException e) {
			throw new DiscoveryWorkerException(
					"Failed to parse the site URL: '" + siteUrl + "'");
		} catch (Exception e) {
			throw new DiscoveryWorkerException(
					"Failed to get Individual URIS for site '" + siteUrl
							+ "', class '" + classUri + "'");
		}
	}

	private HttpWorkerRequest<Document> figureNextPageRequest(String siteUrl,
			String classUri, int pageIndex) throws DiscoveryWorkerException {
		try {
			return http.get(siteUrl + "/individuallist")
					.parameter("page", pageIndex)
					.parameter("vclassId", classUri).asHtml();
		} catch (HttpWorkerException e) {
			throw new DiscoveryWorkerException("Failed to compose the request "
					+ "for the next page of the Individual List: site='"
					+ siteUrl + "', class='" + classUri + "', pageIndex="
					+ pageIndex);
		}
	}

	/**
	 * Fetch a page of search results. Add them to the collected results. When
	 * we get a page with no results, we are done.
	 */
	private boolean fetchAndParsePage(URL siteUrl,
			HttpWorkerRequest<Document> pageRequest, List<String> result)
			throws DiscoveryWorkerException {
		try {
			Document pageDoc = pageRequest.execute();

			List<String> uris = new ArrayList<>();
			Elements links = pageDoc.select("a[href][title=individual name]");
			for (Element link : links) {
				String href = link.attr("href");
				try {
					uris.add(new URL(siteUrl, href).toExternalForm());
				} catch (Exception e) {
					throw new DiscoveryWorkerException(
							"Cannot make a valid URL from '" + href
									+ "', relative to '" + siteUrl + "'", e);
				}
			}

			if (uris.isEmpty()) {
				return true;
			} else {
				result.addAll(uris);
				return false;
			}
		} catch (HttpWorkerException e) {
			throw new DiscoveryWorkerException("Problem fetching HTML at "
					+ pageRequest, e);
		} catch (Exception e) {
			throw new DiscoveryWorkerException("Failed to fetch and parse "
					+ "HTML pageL at " + pageRequest, e);
		}
	}
}
