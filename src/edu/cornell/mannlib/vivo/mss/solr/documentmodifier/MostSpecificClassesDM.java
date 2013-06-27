/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mss.solr.filters.MostSpecificClassesFilter;

/**
 * Fill in the fields for "most_specific_class" and "most_specific_class_label".
 */
public class MostSpecificClassesDM implements DocumentModifier {
	private final MostSpecificClassesFilter filter;

	public MostSpecificClassesDM(
			MostSpecificClassesFilter filter) {
		this.filter = filter;
	}

	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m) {
		doc.addField("most_specific_class", getMostSpecificClassUris(m));
		doc.addField("most_specific_class_label", getMostSpecificClassLabels(m));
	}

	private Object getMostSpecificClassUris(Model m) {
		// TODO
		return Collections.emptySet();
	}

	private Object getMostSpecificClassLabels(Model m) {
		// TODO
		return Collections.emptySet();
	}

}
