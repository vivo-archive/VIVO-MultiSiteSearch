package edu.cornell.mannlib.vivo.mss.hadoop;

import org.apache.http.impl.client.DefaultHttpClient;

import edu.cornell.mannlib.vivo.mss.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.HttpLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.LinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mss.solr.BasicSolrIndexService;
import edu.cornell.mannlib.vivo.mss.solr.DocumentMaker;
import edu.cornell.mannlib.vivo.mss.solr.BasicDocumentMaker;
import edu.cornell.mannlib.vivo.mss.solr.SolrIndexService;

/**
 * This class is an example of a specific trivial specialization of the
 * IndexUris class for Vivo sites.
 * 
 */
class VivoIndexUris extends BaseIndexUris {

	@Override
	protected LinkedDataService setupLinkedDataSource(Context context) {
		return new ExpandingLinkedDataService(new HttpLinkedDataService(
				new DefaultHttpClient()), new UrisToExpand(
				UrisToExpand.getVivoTwoHopPredicates(),
				UrisToExpand.getDefaultSkippedPredicates(),
				UrisToExpand.getDefaultSkippedResourceNS()));
	}

	@Override
	protected DocumentMaker setupDocMaker(Context context) {
		return new BasicDocumentMaker();
	}

	@Override
	protected SolrIndexService setupSolrServer(Context context) {
		String solrUrl = context.getConfiguration()
				.get(BuildIndexUtils.solrUrl);
		return new BasicSolrIndexService(solrUrl);     
	}

}
