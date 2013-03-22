package edu.cornell.mannlib.vivo.mms.discovery.testtarget;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisForSite;

public class TestDiscovery implements DiscoverUrisForSite {
	private static final Log log = LogFactory.getLog(TestDiscovery.class);
	
	@Override
	public Iterable<String> getUrisForSite(String siteUrl, DiscoverUrisContext duContext) {
		System.out.println("TestDiscovery BOGUS");
		log.error("getUrisForSite method BOGUS");
		log.debug("getUrisForSite method");
		return Arrays.asList(
				"http://vivo.cornell.edu/individual/individual19589",
				"http://vivo.cornell.edu/individual/JamesBlake",
				"http://vivo.cornell.edu/individual/individual24416",
				"http://vivo.cornell.edu/individual/individual5320",
				"http://vivo.cornell.edu/individual/individual6059"
				);
	}

}
