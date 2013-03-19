package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
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
		