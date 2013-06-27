/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Fill the fields for "name", "title", and "thumbnail_URL".
 */
public class BasicFieldsDM implements DocumentModifier {
	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m) {
		doc.addField("name", bestName(m));
		doc.addField("title", bestTitle(m));
		doc.addField("thumbnail_URL", getThumbnailUrl(m));
	}

	private Object bestName(Model m) {
		// TODO
		// preferred name if there is one, or else label.
		// Should be the same as the VIVO individual page.
		return Collections.emptySet();
	}

	private Object bestTitle(Model m) {
		// TODO
		// preferred title if there is one, or else label of the most specific
		// class.
		// Should be the same as the VIVO individual page.
		return Collections.emptySet();
	}

	private Object getThumbnailUrl(Model m) {
		// TODO
		// This should be obvious
		return Collections.emptySet();
	}

}
