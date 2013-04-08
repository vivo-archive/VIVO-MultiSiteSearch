package edu.cornell.mannlib.vivo.mss.hadoop;

import org.apache.http.impl.client.DefaultHttpClient;

import edu.cornell.mannlib.vivo.mss.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.HttpLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mss.solr.DocumentMakerImpl;

/**
 * This class is an example of a specific trivial 
 * specialization of the IndexUris class for Vivo sites.
 *  
 */
class VivoIndexUris extends BaseIndexUris{

    @Override
    protected void setupLinkedDataSource(Context context) {
        dataSource = 
            new ExpandingLinkedDataService(
                new HttpLinkedDataService(new DefaultHttpClient()),
                new UrisToExpand(UrisToExpand.getVivoTwoHopPredicates(), 
                                 UrisToExpand.getDefaultSkippedPredicates(), 
                                 UrisToExpand.getDefaultSkippedResourceNS()));
    }

    @Override
    protected void setupDocMaker(Context context) {
        docMaker = new DocumentMakerImpl();        
    }
	
}
