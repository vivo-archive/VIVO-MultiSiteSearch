package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.Arrays;

import org.apache.hadoop.mapreduce.Mapper.Context;

public class TestDiscovery implements DiscoverUrisForSite {

	@Override
	public Iterable<String> getUrisForSite(String siteUrl, Context context) {
		return Arrays.asList(
				"http://vivo.cornell.edu/individual/individual19589",
				"http://vivo.cornell.edu/individual/JamesBlake",
				"http://vivo.cornell.edu/individual/individual24416",
				"http://vivo.cornell.edu/individual/individual5320",
				"http://vivo.cornell.edu/individual/individual6059"
				);
	}

}
