/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mss.solr.filters.CoreClassesFilter;

/**
 * Fill the fields for "core_class" and "core_class_label".
 */
public class CoreClassesDM implements DocumentModifier {
	private final CoreClassesFilter filter;

	public CoreClassesDM(CoreClassesFilter filter) {
		this.filter = filter;
	}

	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m) {
		doc.addField("core_class", getCoreClassUris(m));
		doc.addField("core_class_label", getCoreClassLabels(m));
	}

	private Object getCoreClassUris(Model m) {
		// TODO
		// Get the URIs of the core rdfs:types
		// TODO What are those?
		return Collections.emptySet();
	}

	private Object getCoreClassLabels(Model m) {
		// TODO
		// Get the labels of the core rdfs:types
		// TODO What are those?
		return Collections.emptySet();
	}

}
