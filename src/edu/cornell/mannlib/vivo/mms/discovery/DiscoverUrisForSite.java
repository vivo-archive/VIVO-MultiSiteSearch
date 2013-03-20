package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.Iterator;

import org.apache.hadoop.mapreduce.Mapper.Context;

public interface DiscoverUrisForSite {
	
	public Iterable<String> getUrisForSite(String siteUrl, Context context);
		

}
