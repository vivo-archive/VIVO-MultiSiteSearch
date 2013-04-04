/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package testAlone.localhost14;

import java.util.Arrays;
import java.util.List;

import livetest.tools.LimitedUseHttpWorker;

import org.apache.log4j.Level;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisUsingIndividualList;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpClientFactory;

/**
 * Run a livetest against localhost, using hard-coded class URIS and site URL, a
 * hardcoded implementation of DiscoverUrisForSite, but actual HTTP transfers.
 * 
 * Run outside of Hadoop.
 */
public class TestSearchDiscoveryOnLocalhost14 {
	private static final List<String> CLASS_URIS = Arrays.asList(
			"http://vivoweb.org/ontology/core#Continent",
			"http://xmlns.com/foaf/0.1/Person");

	public static void main(String[] args) throws DiscoveryWorkerException {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);

		DiscoveryWorker worker = new DiscoverUrisUsingIndividualList(
				CLASS_URIS, new LimitedUseHttpWorker(10,
						HttpClientFactory.standardClient()));
		Iterable<String> uris = worker
				.getUrisForSite("http://localhost:8080/vivo14");
		for (String uri : uris) {
			System.out.println(uri);
		}
	}
}
