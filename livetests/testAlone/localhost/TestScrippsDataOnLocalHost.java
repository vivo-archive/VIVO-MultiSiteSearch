/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package testAlone.localhost;

import java.util.Arrays;
import java.util.List;

import livetest.tools.RelocatingHttpLinkedDataService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;

import com.google.common.collect.Iterables;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.cornell.mannlib.vivo.mss.discovery.DiscoverUrisUsingSearchPages;
import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorkerException;
import edu.cornell.mannlib.vivo.mss.linkedData.ExpandingLinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.LinkedDataService;
import edu.cornell.mannlib.vivo.mss.linkedData.UrisToExpand;
import edu.cornell.mannlib.vivo.mss.solr.BasicSolrIndexService;
import edu.cornell.mannlib.vivo.mss.solr.SolrIndexService;
import edu.cornell.mannlib.vivo.mss.solr.documentMaker.DocumentMaker;
import edu.cornell.mannlib.vivo.mss.solr.documentMaker.StandardVivoDocumentMaker;
import edu.cornell.mannlib.vivo.mss.utils.Log4JHelper;
import edu.cornell.mannlib.vivo.mss.utils.http.BasicHttpWorker;
import edu.cornell.mannlib.vivo.mss.utils.http.HttpClientFactory;
import edu.cornell.mannlib.vivo.mss.utils.monitoring.ElapsedTime;

/**
 * TODO
 */
public class TestScrippsDataOnLocalHost {
	private static final Log log = LogFactory
			.getLog(TestScrippsDataOnLocalHost.class);

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
		System.out.println("Found " + Iterables.size(uris) + " uris.");
		for (String uri : uris) {
			log.info(uri);
			try {
				Model m = ModelFactory.createDefaultModel();

				ElapsedTime.linkedData.start();
				lds.getLinkedData(uri, m);
				ElapsedTime.linkedData.end();

				ElapsedTime.solr.start();
				sis.add(dm.makeDocument(uri, m));
				ElapsedTime.solr.end();
			} catch (Exception e) {
				log.error("Failure on URI: " + uri + ", " + e);
			}
		}
		System.out.println("\n---------------------------\n\n");
		System.out.println(ElapsedTime.linkedData);
		System.out.println(ElapsedTime.solr);
		System.out.println("\n---------------------------\n\n");
	}

	private static void configureLogging() {
		Log4JHelper.resetToConsole();
		Log4JHelper.setLoggingLevel(Level.WARN);
		Log4JHelper.setLoggingLevel("edu.cornell", Level.INFO);
	}

	private static void createDataService() {
		lds = new ExpandingLinkedDataService(
				new RelocatingHttpLinkedDataService(
						HttpClientFactory.standardClient(),
						"http://vivo.scripps.edu/individual/",
						"http://localhost:8080/vivo"), new UrisToExpand(
						UrisToExpand.getVivoTwoHopPredicates(),
						UrisToExpand.getDefaultSkippedPredicates(),
						UrisToExpand.getDefaultSkippedResourceNS()));
	}

	private static void createDocumentMaker() {
		dm = new StandardVivoDocumentMaker("Scripps data",
				"http://vivo.scripps.edu");
	}

	private static void createSolrService() {
		sis = new BasicSolrIndexService("http://localhost:8983/solr");
	}

	private static Iterable<String> discover() throws DiscoveryWorkerException {
		DiscoveryWorker worker = new DiscoverUrisUsingSearchPages(CLASS_URIS,
				new BasicHttpWorker(HttpClientFactory.standardClient()));
		Iterable<String> uris = worker
				.getUrisForSite("http://localhost:8080/vivo");
		for (String uri : uris) {
			System.out.println(uri);
		}
		return uris;
	}

}
