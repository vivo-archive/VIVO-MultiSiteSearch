/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package testAlone.localhost;

import java.util.Arrays;
import java.util.List;

import livetest.tools.RelocatingHttpLinkedDataService;

import org.apache.log4j.Level;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.cornell.mannlib.vivo.mss.discovery.DiscoverUrisUsingListrdf;
import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mss.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.LinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mss.utils.Log4JHelper;
import edu.cornell.mannlib.vivo.mss.utils.http.BasicHttpWorker;
import edu.cornell.mannlib.vivo.mss.utils.http.HttpClientFactory;

/**
 * TODO
 */
public class TestDiscoverAndExpandOnLocalHost {
	private static final List<String> CLASS_URIS = Arrays
			.asList("http://xmlns.com/foaf/0.1/Person");

	public static void main(String[] args) throws Exception {
		configureLogging();
		Iterable<String> uris = discover();
		Model m = expand(uris);
		m.write(System.out);
	}

	private static void configureLogging() {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);
	}

	private static Iterable<String> discover() throws DiscoveryWorkerException {
		DiscoveryWorker worker = new DiscoverUrisUsingListrdf(CLASS_URIS,
				new BasicHttpWorker(HttpClientFactory.standardClient()));
		Iterable<String> uris = worker
				.getUrisForSite("http://localhost:8080/vivo");
		for (String uri : uris) {
			System.out.println(uri);
		}
		return uris;
	}

	private static Model expand(Iterable<String> uris) throws Exception {
		LinkedDataService lds = new ExpandingLinkedDataService(
				new RelocatingHttpLinkedDataService(
						HttpClientFactory.standardClient(),
						"http://vivo.mydomain.edu/individual/",
						"http://localhost:8080/vivo"), new UrisToExpand(
						UrisToExpand.getVivoTwoHopPredicates(),
						UrisToExpand.getDefaultSkippedPredicates(),
						UrisToExpand.getDefaultSkippedResourceNS()));

		Model m = ModelFactory.createDefaultModel();
		for (String uri : uris) {
			lds.getLinkedData(uri, m);
		}
		return m;
	}

}
