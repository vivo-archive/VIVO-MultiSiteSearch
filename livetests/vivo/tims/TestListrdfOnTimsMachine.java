/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package vivo.tims;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mms.discovery.smallvivosite.DiscoverUrisUsingListrdf;
import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;
import edu.cornell.mannlib.vivo.mms.utils.http.BasicHttpWorker;

/**
 * Test against Tim's fully-populated mirror of VIVO Cornell
 */
public class TestListrdfOnTimsMachine {
	private static final List<String> CLASS_URIS = Arrays.asList(
			"http://xmlns.com/foaf/0.1/Person",
			"http://vivoweb.org/ontology/core#Continent");

	public static void main(String[] args) throws DiscoveryWorkerException {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);

		DiscoveryWorker worker = new DiscoverUrisUsingListrdf(CLASS_URIS,
				new BasicHttpWorker());
		Iterable<String> uris = worker
				.getUrisForSite("http://tlw72-dev.library.cornell.edu:8080/vivocornell");
		for (String uri : uris) {
			System.out.println(uri);
		}
	}

}
