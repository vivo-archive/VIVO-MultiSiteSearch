/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Fill the fields for "class" and "class_label".
 */
public class ClassesDM implements DocumentModifier {
	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m) {
		doc.addField("class", getClassUris(m));
		doc.addField("class_label", getClassLabels(m));
	}

	private Object getClassUris(Model m) {
		// TODO
		// Get the URIs of the rdfs:types
		return Collections.emptySet();
	}

	private Object getClassLabels(Model m) {
		// TODO
		// Get the labels of the rdfs:types
		return Collections.emptySet();
	}

}
