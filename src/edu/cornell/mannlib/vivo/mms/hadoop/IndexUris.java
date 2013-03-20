package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This class accepts a list of URIs to index and then gets the linked data for the
 * URI, builds a solr document and adds that to a solr index.
 * 
 *  Input: List of siteURL -> URI pairs.
 *  ex.
 *  [ "http://vivo.cornell.edu" : "http://vivo.cornell.edu/individual1343" ... ]
 *  
 *  Output: TBD. Maybe success or error for each URI?
 *  
 */
class IndexUris  extends Mapper<Text, Text, Text, Text>{
	Log log = LogFactory.getLog(IndexUris.class);

	@Override
	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {

//		//TODO:				
//		// get linked data
//		//expand link data
//		//make solr document 
//		//index document in solr server
//		
//		//we could write to output but we sort of don't need to 
//		//since we are writing to solr.				
		context.write(value, new  Text("SUCCESS"));
		
	}

	
}