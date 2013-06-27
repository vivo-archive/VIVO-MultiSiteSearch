/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mss.solr.filters.ClassGroupsFilter;

/**
 * Fill the fields for "classgroup" and "classgroup_label".
 */
public class ClassGroupsDM implements DocumentModifier {
	private ClassGroupsFilter filter;

	public ClassGroupsDM(ClassGroupsFilter filter) {
		this.filter = filter;
	}

	@Override
	public void modifyDocument(SolrInputDocument doc, String uri, Model m) {
		doc.addField("classgroup", getClassGroupUris(m));
		doc.addField("classgroup_label", getClassGroupLabels(m));
	}

	private Object getClassGroupUris(Model m) {
		// TODO
		return Collections.emptySet();
	}

	private Object getClassGroupLabels(Model m) {
		// TODO
		return Collections.emptySet();
	}

}
