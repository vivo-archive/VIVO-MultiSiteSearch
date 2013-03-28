package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisForSite;
import edu.cornell.mannlib.vivo.mms.utils.HadoopContextHelper;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;

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
public class VivoUriDiscovery extends UriDiscovery {

	private DiscoverUrisForSite uriSource;
	private Document siteConfigDoc;


	@Override
	protected void map(LongWritable lineNum, Text urlOfSite, Context context)
			throws IOException, InterruptedException {
		DiscoverUrisContext duContext;
		try {
			duContext = DiscoverUrisContext.wrap(context, siteConfigDoc);
		} catch (Exception e) {
			throw new Error("Failed to create the DiscoverUrisContext.", e);
		}

		String siteUrl = urlOfSite.toString();
		for (String uri : uriSource.getUrisForSite(siteUrl, duContext)) {
			context.getCounter(BuildIndexUtils.Counters.URIS_DISCOVERED)
					.increment(1);
			context.write(urlOfSite, new Text(uri));
		}
	}

    protected DiscoverUrisForSite getDiscoverUrisForSite( Context context ){
        return 
            HadoopContextHelper
            .instantiateFromProperty(context,BuildIndexUtils.discoveryImpl, DiscoverUrisForSite.class);
    }

    protected Document getSiteConfigDoc( Context context ){
        return             
            HadoopContextHelper.parseXmlFileAtProperty(context,
				BuildIndexUtils.siteConfigLocation);
    }
}
