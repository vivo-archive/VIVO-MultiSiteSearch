package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisForSite;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;


/*
 * This is a suggestion of how something like this could be done.
 * Sub classes of this would define how to make the DiscoverUrisForSite
 * object by directly constructing it.
 */

/**
 * Get all the URIs for a given site.
 * 
 * INPUT: This mapper expects Text inputs that are URLs of sites. No keys are
 * expected. ex. [ "http://vivo.cornell.edu" ]
 * 
 * OUTPUT: URIs of individuals from the sites that should be added to the index.
 * The key should be the site URL and the value should be the URI of an
 * individual from that site. ex. [ "http://vivo.cornell.edu" :
 * "http://vivo.cornell.edu/indiviudal134" ... ]
 */
public abstract class BaseUriDiscovery extends Mapper<LongWritable, Text, Text, Text> {
	Log log = LogFactory.getLog(BaseUriDiscovery.class);

	private DiscoverUrisForSite uriSource;

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Log4JHelper.addConfigfile("log4j.job.properties");
		super.setup(context);

        throw new Error("This interface doesn't work any more");
		//this.uriSource = getDiscoverUrisForSite(context);
	}

	@Override
	protected void map(LongWritable lineNum, Text urlOfSite, Context context)
			throws IOException, InterruptedException {		
		
		// String siteUrl = urlOfSite.toString();
		// for (String uri : uriSource.getUrisForSite(siteUrl)) {
		// 	context.getCounter(BuildIndexUtils.Counters.URIS_DISCOVERED)
		// 			.increment(1);
		// 	context.write(urlOfSite, new Text(uri));
		// }
	}
   
    //public abstract DiscoveryUrisForSite getDiscoverUrisForSite( Context context );
}