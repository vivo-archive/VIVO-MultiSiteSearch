/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package vivo.tims;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Level;

import com.google.common.collect.Iterables;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.discovery.smallvivosite.DiscoverUrisUsingListrdf;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;
import edu.cornell.mannlib.vivo.mms.utils.http.BasicHttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker;

/**
 * Test against Tim's fully-populated mirror of VIVO Cornell
 */
public class TestListrdfOnTimsMachine {
	public static void main(String[] args) {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);

		try {
			long startTime = System.currentTimeMillis();
			Iterable<String> uris = new DiscoverUrisUsingListrdf()
					.getUrisForSite(
							"http://tlw72-dev.library.cornell.edu:8080/vivocornell",
							new DiscoverUrisContextForTim());
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			double seconds = duration / 1000;
			System.out.println(String.format(
					"Discovered %1d URIs in %2f.3 seconds",
					Iterables.size(uris), seconds));
		} catch (Exception e) {
			System.err.println("Ended with Exception: " + e);
			e.printStackTrace();
		} catch (Error e) {
			System.err.println("Ended with Error: " + e);
			e.printStackTrace();
		}
	}

	private static class DiscoverUrisContextForTim extends DiscoverUrisContext {

		@Override
		public Collection<String> getClassUris(String siteUrl) {
			List<String> uris = new ArrayList<>();
			uris.add("http://xmlns.com/foaf/0.1/Person");
			uris.add("http://vivoweb.org/ontology/core#Continent");
			return uris;
		}

		@Override
		public HttpWorker getHttpWorker() {
			return new BasicHttpWorker();
		}

	}
}
