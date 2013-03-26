/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.smallvivosite;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mms.AbstractTestClass;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;

/**
 * TODO
 */
public class DiscoverUrisUsingListrdfTest extends AbstractTestClass {
	/**
	 * 
	 */
	private static final String HARDCODED_RDF = "<rdf:RDF\n"
			+ "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
			+ "    xmlns:j.0=\"http://xmlns.com/foaf/0.1/\" > \n"
			+ "  <rdf:Description rdf:about=\"http://vivo.mydomain.edu/individual/n5638\">\n"
			+ "    <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n"
			+ "  </rdf:Description>\n" + "</rdf:RDF>\n";

	private static final Document HARDCODED_RDF_DOC = parseXml(HARDCODED_RDF);

	private DiscoverUrisUsingListrdf urlFinder;
	private DiscoverUrisContext duContext;

	private static Document parseXml(String xml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			return docBuilder.parse(new InputSource(new StringReader(xml)));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(
					"Failed to parse a constant XML string:", e);
		}
	}

	@Test
	public void parseHardcodedResult() {
		duContext = new DiscoverUrisContext() {

			@Override
			public HttpWorker getHttpWorker() {
				return new HttpWorker() {

					@Override
					public Document getRdfXml(String url,
							Parameter... parameters) {
						return HARDCODED_RDF_DOC;
					}

					@Override
					public String getRdfString(String url,
							Parameter... parameters) throws HttpWorkerException {
						return HARDCODED_RDF;
					}

					@Override
					public Model getRdfModel(String url,
							Parameter... parameters) throws HttpWorkerException {
						throw new UnsupportedOperationException("HttpWorker.getRdfModel() not implemented.");
					}

				};
			}

			@Override
			public Collection<String> getClassUris(String siteUrl) {
				return Collections.singleton("junk");
			}
		};

		urlFinder = new DiscoverUrisUsingListrdf();

		assertUnorderedActualIterable("URIs",
				urlFinder.getUrisForSite("BOGUS", duContext),
				"http://vivo.mydomain.edu/individual/n5638");
	}

}
