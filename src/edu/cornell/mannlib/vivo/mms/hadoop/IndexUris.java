package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

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
class IndexUris  extends Mapper<LongWritable , Text, Text, Text>{
	Log log = LogFactory.getLog(IndexUris.class);

	@Override
	protected void map(LongWritable lineNum, Text value, Context context)
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