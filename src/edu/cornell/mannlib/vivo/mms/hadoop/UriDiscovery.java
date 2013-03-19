package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * Get all the URIs for a given site.
 * 
 * INPUT: This mapper expects Text inputs that are URLs of sites. 
 * OUTPUT: URIs of individuals from the sites that should be added to the index.
 * 
 */
public class UriDiscovery   extends Mapper<Text, Text, Text, Text>{
		Log log = LogFactory.getLog(UriDiscovery.class);

		static enum MyCounters { NUM_URIS_DISCOVERED};

		void map(
				Text key, Text value,
				OutputCollector<Text,Text> output,
				Reporter reporter) throws IOException {
			
			String siteURL = value.toString();
			
			//TODO: implement the loop to discovery the URIs for the site
				
			//Report that we found something
			//reporter.incrCounter(NUM_URIS_DISCOVERED, 1);
			
			//write the found URI to output						
			Text uriToIndex = new Text("http://localhost/individual1234");			
			output.collect(value , uriToIndex );
		}

		public static boolean uriDiscoveryComplete(Configuration conf) {
			// TODO Auto-generated method stub
			return false;
		}
}
		