/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.vivotarget;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker.HttpWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker.Parameter;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper;

/**
 * TODO
 */
public class UrlsForClassForVivoSite {
	private static final String XPATH_TO_INDIVIDUAL_URI = "//rdf:Description/@rdf:about";

	private final String siteUrl;
	private final String classUri;
	private final DiscoverUrisContext duContext;

	public UrlsForClassForVivoSite(String siteUrl, String classUri,
			DiscoverUrisContext duContext) {
		this.siteUrl = siteUrl;
		this.classUri = classUri;
		this.duContext = duContext;
	}

	/**
	 * <pre>
	 * Get the URIs for each class
	 *   Use an HttpClient (from the context) to hit listrdf 
	 *     e.g. http://localhost:8080/vivo/listrdf?vclass=http://xmlns.com/foaf/0.1/Person
	 *     Return could be 200 bytes per person x 50K persons = 10M in size
	 * </pre>
	 */
	public Iterable<String> getUris() {
		try {
			Document uriList = getRdf();
			return parseUriList(uriList);
		} catch (HttpWorkerException e) {
			throw new Error(
					"Can't continue. Failed to read the URLs for class '"
							+ classUri + "' at site '" + siteUrl + "'");
		}
	}

	private Document getRdf() throws HttpWorkerException {
		HttpWorker http = duContext.getHttpWorker();
		Document uriList = http.getRdfXml(siteUrl + "/listrdf", new Parameter(
				"vclass", classUri));
		return uriList;
	}

	private Iterable<String> parseUriList(Document uriListDoc) {
		Set<String> uris = new HashSet<String>();

		XPathHelper xp = XPathHelper.getHelper(XPathHelper.RDF_PREFIX);
		Node rootNode = uriListDoc.getDocumentElement();
		for (Node node : xp.findNodes(XPATH_TO_INDIVIDUAL_URI, rootNode)) {
			uris.add(node.getNodeValue());
		}

		return uris;
	}

}
