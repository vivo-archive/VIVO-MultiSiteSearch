package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;

class IndexUris <K> extends Mapper<Text, Text, Text, Text>{
	Log log = LogFactory.getLog(IndexUris.class);

	void map(
			Text siteUrl,
			Text uri,
			OutputCollector<Text,Text> output,
			Reporter reporter) throws IOException {

		//TODO:				
		// get linked data
		//expand link data
		//make solr document 
		//index document in solr server
		
		//we could write to output but we sort of don't need to 
		//since we are writing to solr.				
		output.collect(uri, new  Text("SUCCESS"));
	}
	

}