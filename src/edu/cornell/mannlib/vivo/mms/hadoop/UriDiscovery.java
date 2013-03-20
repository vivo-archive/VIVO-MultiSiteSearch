package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * Get all the URIs for a given site.
 * 
 * INPUT: This mapper expects Text inputs that are URLs of sites. No keys are expected.
 * ex.
 * [ "http://vivo.cornell.edu" ]
 * 
 * OUTPUT: URIs of individuals from the sites that should be added to the index. The key 
 * should be the site URL and the value should be the URI of an individual from that site.
 * ex.
 * [ "http://vivo.cornell.edu" : "http://vivo.cornell.edu/indiviudal134" ... ]
 * 
 */
public class UriDiscovery   extends Mapper<Text, Text, Text, Text>{
		Log log = LogFactory.getLog(UriDiscovery.class);

		static enum MyCounters { NUM_URIS_DISCOVERED};


		@Override
		protected void map(Text key, Text urlOfSite, Context context)
				throws IOException, InterruptedException {			

			context.getCounter(MyCounters.NUM_URIS_DISCOVERED).increment(1);
			
			//TODO: implement the loop to discovery the URIs for the site		
			//write the found URI to output						
			Text uriToIndex = new Text("http://localhost/individual1234");			
			context.write( urlOfSite, uriToIndex);
		}

}
		