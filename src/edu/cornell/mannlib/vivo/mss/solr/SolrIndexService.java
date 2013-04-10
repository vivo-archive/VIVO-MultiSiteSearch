/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr;

import org.apache.solr.common.SolrInputDocument;

/**
 * TODO
 */
public interface SolrIndexService {

	/**
	 * Add this document to the index
	 */
	void add(SolrInputDocument doc) throws SolrIndexServiceException;

}
