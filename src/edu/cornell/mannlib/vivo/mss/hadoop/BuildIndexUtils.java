package edu.cornell.mannlib.vivo.mss.hadoop;

public class BuildIndexUtils {
	
	public static final String solrUrl = "BuildIndex.solrUrl";
	public static final String workingDir = "BuildIndex.workingDir";

	public static final String cacheDir = "BuildIndex.cacheDir";

    /** Number of http client connections per route (per mapper task) when getting RDF */
    public static final String connectionsPerRoute = "connectionsPerRoute";
    
    /** Which implementation of DiscoverUrisForSite should we use? */
	public static final String discoveryImpl = "BuildIndex.discoveryImpl";
	
	/** Where is the site Configuration file located? */
	public static final String siteConfigLocation = "BuildIndex.siteConfiguration";
	
	// these can be used with counters from the context
	//ex. context.getCounter(Counters.URIS_DISCOVERED).increment(1);
	public static enum Counters {
		   URIS_DISCOVERED,			
		   HTTP_LD_REQUESTS,
		   HTTP_LD_CACHE_MISSES,
		   SOLR_DOCS_INDEXED
	}

	
	
	//public static Path getWorkingDir(config? context?)
	
  	
}
