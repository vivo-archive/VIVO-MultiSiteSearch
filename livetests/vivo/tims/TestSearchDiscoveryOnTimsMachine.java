package vivo.tims;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Level;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.discovery.largevivosite.DiscoverUrisUsingSearchPages;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorkerImpl;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

/**
 * Run a livetest against Tim's machine, using hard-coded class URIS and site URL, a
 * hardcoded implementation of DiscoverUrisForSite, but actual HTTP transfers.
 * 
 * Run outside of Hadoop.
 */
public class TestSearchDiscoveryOnTimsMachine {
	public static void main(String[] args) {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);

		try {
			Iterable<String> uris = new DiscoverUrisUsingSearchPages()
					.getUrisForSite("http://tlw72-dev.library.cornell.edu:8080/vivocornell",
							new DiscoverUrisContextForTimsMachine());
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
	
	private static class DiscoverUrisContextForTimsMachine extends DiscoverUrisContext {

		@Override
		public Collection<String> getClassUris(String siteUrl) {
			List<String> uris = new ArrayList<>();
			uris.add("http://xmlns.com/foaf/0.1/Person");
//			uris.add("http://vivoweb.org/ontology/core#Continent");
			return uris;
		}

		@Override
		public HttpWorker getHttpWorker() {
			return new HttpWorkerImpl();
		}

	}

}
