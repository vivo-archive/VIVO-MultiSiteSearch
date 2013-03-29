package edu.cornell.mannlib.vivo.mms.hadoop;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import org.apache.hadoop.mapreduce.Mapper.Context;
import edu.cornell.mannlib.vivo.mms.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mms.linkedData.HttpLinkedDataService;
import edu.cornell.mannlib.vivo.mms.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mms.solr.DocumentMakerImpl;

/**
 * This class is an example of a specific trivial 
 * specialization of the IndexUris class for Vivo sites.
 *  
 */
class VivoIndexUris extends BaseIndexUris{

    @Override
    protected LinkedDataSource setupLinkedDataSource(Context context) {
        return new ExpandingLinkedDataService(
                   new HttpLinkedDataService(new DefaultHttpClient()),
                   new UrisToExpand(UrisToExpand.getVivoTwoHopPredicates(), 
                                    UrisToExpand.getDefaultSkippedPredicates(), 
                                    UrisToExpand.getDefaultSkippedResourceNS()));
    }

    @Override
    protected void setupDocMaker(Context context) {
        return new DocumentMakerImpl();        
    }
	
}
