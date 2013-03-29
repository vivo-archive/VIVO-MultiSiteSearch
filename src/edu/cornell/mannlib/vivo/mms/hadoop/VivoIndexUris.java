package edu.cornell.mannlib.vivo.mms.hadoop;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import edu.cornell.mannlib.vivo.mms.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mms.linkedData.HttpLinkedDataService;
import edu.cornell.mannlib.vivo.mms.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mms.solr.DocumentMakerImpl;

/**
 * This class is an example of a specific trivial 
 * specialization of the IndexUris class for Vivo sites.
 *  
 */
class VivoIndexUris extends IndexUris{

    @Override
    protected void setupLinkedDataSource(
            org.apache.hadoop.mapreduce.Mapper.Context context) {
        dataSource = 
            new ExpandingLinkedDataService(
                new HttpLinkedDataService(new DefaultHttpClient()),
                new UrisToExpand(UrisToExpand.getVivoTwoHopPredicates(), 
                                 UrisToExpand.getDefaultSkippedPredicates(), 
                                 UrisToExpand.getDefaultSkippedResourceNS()));
    }

    @Override
    protected void setupDocMaker(
            org.apache.hadoop.mapreduce.Mapper.Context context) {
        docMaker = new DocumentMakerImpl();        
    }
	
}
