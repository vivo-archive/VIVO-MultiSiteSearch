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


public class ContextHelperInstanciateFromPropertiesUriDiscovery extends Mapper<LongWritable, Text, Text, Text> {

	private DiscoverUrisForSite uriSource;
	private Document siteConfigDoc;

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		Log4JHelper.addConfigfile("log4j.job.properties");
		super.setup(context);
		this.uriSource = getDiscoverUrisForSite(context);
		this.siteConfigDoc = getSiteConfigDoc( context );
            
	}

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
