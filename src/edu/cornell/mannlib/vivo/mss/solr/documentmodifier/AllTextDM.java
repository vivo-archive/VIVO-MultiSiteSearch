/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Figure out what goes into the "alltext" field.
 */
public class AllTextDM implements DocumentModifier {
	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m) {
		doc.addField("alltext", assembleAllText(m));
	}

	private Object assembleAllText(Model m) {
		// TODO
		// What do we do here?
		return Collections.emptySet();
	}

}
