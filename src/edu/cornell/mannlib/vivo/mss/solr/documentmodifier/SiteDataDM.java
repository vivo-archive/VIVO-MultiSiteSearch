/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Fill in the fields for "site_name" and "site_URL".
 */
public class SiteDataDM implements DocumentModifier {
	private final String siteName;
	private final String siteUrl;

	public SiteDataDM(String siteName, String siteUrl) {
		this.siteName = siteName;
		this.siteUrl = siteUrl;
	}

	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m){
		doc.addField("site_name", siteName);
		doc.addField("site_URL", siteUrl);
	}

}
