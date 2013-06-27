/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentmodifier;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mss.solr.documentMaker.DocumentMakerException;

/**
 * Use the subject URI and the statements in the model to modify the Solr
 * document, if appropriate.
 */
public interface DocumentModifier {
	public void modifyDocument(SolrInputDocument doc, String uri, Model m)
			throws DocumentMakerException;
}
