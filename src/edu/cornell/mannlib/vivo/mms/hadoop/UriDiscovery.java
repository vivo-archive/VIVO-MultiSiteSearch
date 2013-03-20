package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisForSite;


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
public class UriDiscovery   extends Mapper<LongWritable, Text, Text, Text>{
		Log log = LogFactory.getLog(UriDiscovery.class);
		
		private DiscoverUrisForSite uriSource;
		
		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {			
			super.setup(context);
			
			String classname = context.getConfiguration().get(BuildIndexUtils.discoveryImpl) ;
			Class disClass;
			try {				
				disClass = Class.forName(  classname );
			} catch (ClassNotFoundException e) {
				throw new Error("Cannot continue because could not load " +
						"DiscoveryUrisForSite implementation of " + classname + " " + e.getMessage());
			}
			
			try {
				uriSource= (DiscoverUrisForSite) disClass.newInstance();
			} catch (Exception e) {
				throw new Error("Cannot continue because could not get new instance of " 
						+ classname + " " + e.getMessage());
			}
					
		}

		@Override
		protected void map(LongWritable lineNum, Text urlOfSite, Context context)
				throws IOException, InterruptedException {
			
			for( String uri: uriSource.getUrisForSite(urlOfSite.toString(), context)){				
				context.getCounter(BuildIndexUtils.coutners.URIS_DISCOVERED).increment(1);				
				context.write( urlOfSite, new Text( uri ) );
			}
		}

}
		