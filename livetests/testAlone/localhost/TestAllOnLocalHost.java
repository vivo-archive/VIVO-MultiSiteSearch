/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package testAlone.localhost;

import java.util.Arrays;
import java.util.List;

import livetest.tools.RelocatingHttpLinkedDataService;

import org.apache.log4j.Level;

import com.google.common.collect.Iterables;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.cornell.mannlib.vivo.mss.discovery.DiscoverUrisUsingListrdf;
import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mss.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.LinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mss.solr.BasicDocumentMaker;
import edu.cornell.mannlib.vivo.mss.solr.BasicSolrIndexService;
import edu.cornell.mannlib.vivo.mss.solr.DocumentMaker;
import edu.cornell.mannlib.vivo.mss.solr.SolrIndexService;
import edu.cornell.mannlib.vivo.mss.utils.Log4JHelper;
import edu.cornell.mannlib.vivo.mss.utils.http.BasicHttpWorker;
import edu.cornell.mannlib.vivo.mss.utils.http.HttpClientFactory;

/**
 * TODO
 */
public class TestAllOnLocalHost {
	private static final List<String> CLASS_URIS = Arrays
			.asList("http://xmlns.com/foaf/0.1/Person");

	private static LinkedDataService lds;
	private static DocumentMaker dm;
	private static SolrIndexService sis;

	public static void main(String[] args) throws Exception {
		configureLogging();
		createDataService();
		createDocumentMaker();
		createSolrService();

		Iterable<String> uris = discover();
		System.out.println("Found " +
				Iterables.size(uris)+" uris.");
		for (String uri : uris) {
			Model m = ModelFactory.createDefaultModel();
			lds.getLinkedData(uri, m);
			sis.add(dm.makeDocument(uri, m));
		}
	}

	private static void configureLogging() {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.DEBUG);
	}

	private static void createDataService() {
		lds = new ExpandingLinkedDataService(
				new RelocatingHttpLinkedDataService(
						HttpClientFactory.standardClient(),
						"http://vivo.mydomain.edu/individual/",
						"http://localhost:8080/vivo"), new UrisToExpand(
						UrisToExpand.getVivoTwoHopPredicates(),
						UrisToExpand.getDefaultSkippedPredicates(),
						UrisToExpand.getDefaultSkippedResourceNS()));
	}

	private static void createDocumentMaker() {
		dm = new BasicDocumentMaker();
	}
	
	private static void createSolrService() {
		sis = new BasicSolrIndexService("http://localhost:8983/solr");
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

}
