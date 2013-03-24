/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.cornell.mannlib.vivo.mms.utils.XPathHelper;

/**
 * Configuration info for all client sites. Looks like this, in XML:
 * 
 * <pre>
 * siteConfig
 *    site url=this
 *       classUri someURI /classUri
 *    /site
 *    site url= that
 *       classUri oneURI /classUri
 *       classUri twoURI /classUri
 *    /site
 * /siteConfig
 * </pre>
 */
public class SiteConfig {
	public static SiteConfig parse(Document siteConfigDoc)
			throws SiteConfigException {
		return new SiteConfig(siteConfigDoc);
	}

	private final Map<String, Site> siteMap;

	public SiteConfig(Document siteConfigDoc) throws SiteConfigException {
		XPathHelper xp = XPathHelper.getHelper();
		Node contextNode = siteConfigDoc.getDocumentElement();
		Map<String, Site> sites = new HashMap<>();

		if (!"siteConfig".equals(contextNode.getNodeName())) {
			throw new SiteConfigException("wrong document type: "
					+ "expecting <siteConfig>, but found <"
					+ contextNode.getNodeName() + ">.");
		}

		for (Node siteNode : xp.findNodes("//site", contextNode)) {
			Site s = new Site(siteNode);
			String siteUrl = s.getSiteUrl();
			if (sites.containsKey(siteUrl)) {
				throw new SiteConfigException("found a duplicate siteUrl: '"
						+ siteUrl + "'");
			}
			sites.put(siteUrl, s);
		}
		this.siteMap = Collections.unmodifiableMap(sites);

		if (this.siteMap.isEmpty()) {
			throw new SiteConfigException("no sites.");
		}
	}

	public Collection<String> getSiteUrls() {
		return siteMap.keySet();
	}

	public Site getSite(String siteUrl) {
		return siteMap.get(siteUrl);
	}

	@Override
	public String toString() {
		return "SiteConfig[" + this.siteMap + "]";
	}

	/**
	 * Configuration info for a client site.
	 */
	public static class Site {
		private final String siteUrl;
		private final List<String> classUris;

		public Site(Node siteNode) throws SiteConfigException {
			XPathHelper xp = XPathHelper.getHelper();

			List<Node> siteUrls = xp.findNodes("@url", siteNode);
			if (siteUrls.isEmpty()) {
				throw new SiteConfigException(
						"found a site with no 'url' attribute.");
			} else {
				this.siteUrl = siteUrls.get(0).getNodeValue();
			}

			List<String> uris = new ArrayList<>();
			for (Node classUriNode : xp.findNodes("classUri", siteNode)) {
				String classUri = classUriNode.getTextContent();
				if (uris.contains(classUri)) {
					throw new SiteConfigException("site '" + siteUrl
							+ "' contains duplicate class URIs for '"
							+ classUri + "'");
				}
				uris.add(classUri);
			}
			this.classUris = Collections.unmodifiableList(uris);
			if (classUris.isEmpty()) {
				throw new SiteConfigException("site '" + this.siteUrl
						+ "' has no class URIs.");
			}
		}

		public String getSiteUrl() {
			return this.siteUrl;
		}

		public Collection<String> getClassUris() {
			return this.classUris;
		}

		@Override
		public String toString() {
			return "Site[url=" + this.siteUrl + ", classUris=" + this.classUris
					+ "]";
		}

	}

	public static class SiteConfigException extends Exception {
		public SiteConfigException(String message, Throwable cause) {
			super("Error in site configuration file: " + message, cause);
		}

		public SiteConfigException(String message) {
			super("Error in site configuration file: " + message);
		}
	}

}
