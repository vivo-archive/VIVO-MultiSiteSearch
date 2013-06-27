/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

/**
 * TODO
 */
public class BasicSolrIndexService implements SolrIndexService {
	private final String solrUrl;
	private final SolrServer solr;

	public BasicSolrIndexService(String solrUrl) {
		this.solrUrl = solrUrl;
		this.solr = new HttpSolrServer(solrUrl);
	}

	@Override
	public void add(SolrInputDocument doc) throws SolrIndexServiceException {
		try {
			solr.add(doc);
		} catch (SolrServerException | IOException e) {
			throw new SolrIndexServiceException(
					"Failed to add this document to the server at '" + solrUrl
							+ "': " + doc, e);
		}
	}
}
