/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.smallvivosite;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.cornell.mannlib.vivo.mms.discovery.AbstractDiscoveryWorkerHarness;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper;
import edu.cornell.mannlib.vivo.mms.utils.XPathHelper.XpathHelperException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker.HttpWorkerException;

/**
 * Do the discovery using [vivo]/listrdf.
 * 
 * This does not work on a large VIVO because listrdf won't bring back more than
 * 30,000 individuals per class.
 */
public class DiscoverUrisUsingListrdf extends AbstractDiscoveryWorkerHarness {
	private static final Log log = LogFactory
			.getLog(DiscoverUrisUsingListrdf.class);

	private static final String XPATH_TO_INDIVIDUAL_URI = "//rdf:Description/@rdf:about";

	@Override
	protected DiscoveryWorker getWorker(String siteUrl, String classUri,
			DiscoverUrisContext duContext) {
		return new Worker(siteUrl, classUri, duContext);
	}

	private static class Worker extends DiscoveryWorker {
		public Worker(String siteUrl, String classUri,
				DiscoverUrisContext duContext) {
			super(siteUrl, classUri, duContext);
		}

		/**
		 * Use vivo/listrdf to get the URIs for the class. Parse the result.
		 */
		@Override
		public Iterable<String> discover() {
			try {
				Document uriList = duContext.getHttpWorker()
						.post(siteUrl + "/listrdf")
						.parameter("vclass", classUri).asXML().execute();
				Set<String> uris = parseUriList(uriList);
				if (uris.size() >= 30000) {
					log.error("Site '" + siteUrl + "' maxed out on 30,000 "
							+ "individual URIs for class '" + classUri + "'");
				}
				return uris;
			} catch (HttpWorkerException | XpathHelperException e) {
				throw new Error(
						"Can't continue. Failed to read the URLs for class '"
								+ classUri + "' at site '" + siteUrl + "'", e);
			}
		}

		private Set<String> parseUriList(Document uriListDoc)
				throws XpathHelperException {
			Set<String> uris = new HashSet<String>();

			XPathHelper xp = XPathHelper.getHelper(XPathHelper.RDF_PREFIX);
			Node rootNode = uriListDoc.getDocumentElement();
			for (Node node : xp.findNodes(XPATH_TO_INDIVIDUAL_URI, rootNode)) {
				uris.add(node.getNodeValue());
			}
			return uris;
		}
	}
}
