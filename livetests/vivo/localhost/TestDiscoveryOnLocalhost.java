package vivo.localhost;

import org.apache.log4j.Level;

import edu.cornell.mannlib.vivo.mms.discovery.vivotarget.DiscoverUrisForVivoSite;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

/**
 * Run a livetest against localhost, using hard-coded class URIS and site URL, a
 * hardcoded implementation of DiscoverUrisForSite, but actual HTTP transfers.
 * 
 * Run outside of Hadoop.
 */
public class TestDiscoveryOnLocalhost {
	public static void main(String[] args) {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);

		try {
			Iterable<String> uris = new DiscoverUrisForVivoSite()
					.getUrisForSite("http://localhost:8080/vivo",
							new DiscoverUrisContextForLocalhost());
			for (String uri : uris) {
				System.out.println(uri);
			}
		} catch (Exception e) {
			System.err.println("Ended with Exception: " + e);
			e.printStackTrace();
		} catch (Error e) {
			System.err.println("Ended with Error: " + e);
			e.printStackTrace();
		}
	}
}
