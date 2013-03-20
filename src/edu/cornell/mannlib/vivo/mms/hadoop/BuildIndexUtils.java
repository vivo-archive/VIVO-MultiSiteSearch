package edu.cornell.mannlib.vivo.mms.hadoop;

public class BuildIndexUtils {
	
	public static final String solrUrl = "BuildIndex.solrUrl";
	public static final String workingDir = "BuildIndex.workingDir";
	public static final String cacheDir = "BuildIndex.cacheDir";

	public static final String discoveryImpl = "BuildIndex.discoveryImpl";
	
	// these can be used with conuters from the context
	//ex. context.getCounter(counters.URIS_DISCOVERED).increment(1);
	public static enum coutners {
		   URIS_DISCOVERED,			
		   HTTP_LD_REQUESTS,
		   HTTP_LD_CACHE_MISSES,
		   SOLR_DOCS_INDEXED
	};
	
	
	//public static Path getWorkingDir(config? context?)
	
  	
}
