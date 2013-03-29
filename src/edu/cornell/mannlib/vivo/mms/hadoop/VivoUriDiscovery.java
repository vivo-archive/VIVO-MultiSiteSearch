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
import edu.cornell.mannlib.vivo.mms.discovery.VivoDiscoverUrisForSite;
import edu.cornell.mannlib.vivo.mms.utils.HadoopContextHelper;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorkerImpl;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;

/**
 * Get all the URIs for a given VIVO site.
 * 
 * INPUT: This mapper expects Text inputs that are URLs of sites. No keys are
 * expected. ex. [ "http://vivo.cornell.edu" ]
 * 
 * OUTPUT: URIs of individuals from the sites that should be added to the index.
 * The key should be the site URL and the value should be the URI of an
 * individual from that site. ex. [ "http://vivo.cornell.edu" :
 * "http://vivo.cornell.edu/indiviudal134" ... ]
 */
public class VivoUriDiscovery extends BaseUriDiscovery{

    @Override
    protected DiscoverUrisForSite getDiscoverUrisForSite( Context context ){
        return new VivoDiscoverUrisForSite( new HttpWorkerImpl() );
    }

}
