package edu.cornell.mannlib.vivo.mms.hadoop;

public class BuildIndexUtils {
	
	public static final String solrUrl = "BuildIndex.solrUrl";
	public static final String workingDir = "BuildIndex.workingDir";

	
	// these can be used with conuters from the context
	//ex. context.getCounter(counters.URIS_DISCOVERED).increment(1);
	public static enum coutners {
		   URIS_DISCOVERED,			
		   HTTP_LD_REQUESTS,
			SOLR_DOCS_INDEXED
	};
	
	//public static Path getWorkingDir(config? context?)
	
  	
}
