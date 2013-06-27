package edu.cornell.mannlib.vivo.mss.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.cornell.mannlib.vivo.mss.linkedData.LinkedDataService;
import edu.cornell.mannlib.vivo.mss.solr.SolrIndexService;
import edu.cornell.mannlib.vivo.mss.solr.documentMaker.DocumentMaker;

/**
 * This class accepts a list of URIs to index and then gets the linked data for the
 * URI, builds a solr document and adds that to a solr index.
 * 
 *  Input: list of lineNum -> URIs to index:
 *  ex.
 *  [ 1 : "http://vivo.cornell.edu/individual4344" ... ]
 *  
 *  Output: TBD. Maybe success or error for each URI?
 *  
 */
public abstract class BaseIndexUris extends Mapper<LongWritable , Text, Text, Text>{
	Log log = LogFactory.getLog(BaseIndexUris.class);

	protected DocumentMaker docMaker;	
	protected SolrIndexService solrServer;
	protected LinkedDataService dataSource;

    /**
     * Implementations must provide a linked data source. 
     */
    protected abstract LinkedDataService setupLinkedDataSource(Context context);

    /**
     * Implementations must provide a document maker.
     */
    protected abstract DocumentMaker setupDocMaker(Context context);

    /**
     * Implementations must provide a Solr server.
     */
    protected abstract SolrIndexService setupSolrServer(Context context);

	
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		docMaker = setupDocMaker(context);
		solrServer = setupSolrServer(context);
		dataSource = setupLinkedDataSource(context);
	}

    @Override
	protected void map(LongWritable lineNum, Text value, Context context)
			throws IOException, InterruptedException {

        String uri = value.toString();
        Model data = null;
        SolrInputDocument doc = null;

        try {
            data = getLinkedData(uri);
        } catch (Throwable ex) {
            log.error(ex, ex);
            context.write(value,
                    new Text("ERROR\tGettingLinkedData\t" + ex.getMessage()));
            return;
        }

        try {
            doc = docMaker.makeDocument(uri, data);
        } catch (Throwable ex) {
            log.error(ex, ex);
            context.write(value,
                    new Text("ERROR\tMakingSolrDoc\t" + ex.getMessage()));
            return;
        }
        
        try {
        	solrServer.add(doc);
        } catch (Throwable ex) {
            log.error(ex, ex);
            context.write(value,
                    new Text("ERROR\tIndexingSolrDoc\t" + ex.getMessage()));
            return;
        }

        context.write(value, new  Text("SUCCESS"));	    
	}

    protected Model getLinkedData(String uri
           ) throws Exception {
    	Model m = ModelFactory.createDefaultModel();
    	dataSource.getLinkedData( uri, m);
        return m; 
    }
	
}
