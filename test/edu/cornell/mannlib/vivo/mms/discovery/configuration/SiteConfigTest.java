/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.configuration;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.cornell.mannlib.vivo.mms.AbstractTestClass;
import edu.cornell.mannlib.vivo.mms.configuration.SiteConfig;
import edu.cornell.mannlib.vivo.mms.configuration.SiteConfig.SiteConfigException;
import edu.cornell.mannlib.vivo.mms.utils.xml.XmlUtils;
import edu.cornell.mannlib.vivo.mms.utils.xml.XmlUtils.XmlUtilsException;

/**
 * Test the parsing of SiteConfig.
 */
public class SiteConfigTest extends AbstractTestClass {
	private static final String WRONG_DOCUMENT_TYPE = "<junk></junk>";
	private static final String NO_SITES = "<siteConfig></siteConfig>";
	private static final String NO_SITE_URL = "<siteConfig><site/></siteConfig>";
	private static final String NO_CLASS_URIS = "<siteConfig><site url='here'/></siteConfig>";
	private static final String DUPLICATE_CLASS_URIS = "<siteConfig>"
			+ "<site url='here'>"
			+ "<classUri>sameClass</classUri><classUri>sameClass</classUri></site>"
			+ "</siteConfig>";
	private static final String DUPLICATE_SITE_URLS = "<siteConfig>"
			+ "<site url='sameUrl'><classUri>cUri</classUri></site>"
			+ "<site url='sameUrl'><classUri>cUri</classUri></site>"
			+ "</siteConfig>";
	private static final String SIMPLE_SUCCESS = "<siteConfig>"
			+ "<site url='oneUrl'><classUri>classOneA</classUri></site>"
			+ "<site url='twoUrl'><classUri>classTwoA</classUri><classUri>classTwoB</classUri></site>"
			+ "</siteConfig>";

	@Test(expected = SiteConfigException.class)
	public void wrongDocumentType() throws SiteConfigException,
			XmlUtilsException {
		SiteConfig.parse(XmlUtils.parseXml(WRONG_DOCUMENT_TYPE));
	}

	@Test(expected = SiteConfigException.class)
	public void noSites() throws SiteConfigException, XmlUtilsException {
		SiteConfig.parse(XmlUtils.parseXml(NO_SITES));
	}

	@Test(expected = SiteConfigException.class)
	public void siteWithoutUrl() throws SiteConfigException, XmlUtilsException {
		SiteConfig.parse(XmlUtils.parseXml(NO_SITE_URL));
	}

	@Test(expected = SiteConfigException.class)
	public void siteWithoutClassUris() throws SiteConfigException,
			XmlUtilsException {
		SiteConfig.parse(XmlUtils.parseXml(NO_CLASS_URIS));
	}

	@Test(expected = SiteConfigException.class)
	public void siteWithDuplicateClassUris() throws SiteConfigException,
			XmlUtilsException {
		SiteConfig.parse(XmlUtils.parseXml(DUPLICATE_CLASS_URIS));
	}

	@Test(expected = SiteConfigException.class)
	public void sitesWithDuplicateUrls() throws SiteConfigException,
			XmlUtilsException {
		SiteConfig.parse(XmlUtils.parseXml(DUPLICATE_SITE_URLS));
	}

	@Test
	public void simpleSuccess() throws SiteConfigException, XmlUtilsException {
		SiteConfig sc = SiteConfig.parse(XmlUtils.parseXml(SIMPLE_SUCCESS));
		assertSiteUrls(sc, "oneUrl", "twoUrl");
		assertClassUris(sc, "oneUrl", "classOneA");
		assertClassUris(sc, "twoUrl", "classTwoA", "classTwoB");
	}

	// ----------------------------------------------------------------------
	// Helper methods
	// ----------------------------------------------------------------------

	private void assertSiteUrls(SiteConfig sc, String... expectedUrls) {
		Set<String> expected = new HashSet<>(Arrays.asList(expectedUrls));
		Set<String> actual = new HashSet<>(sc.getSiteUrls());
		assertEquals("siteUrls", expected, actual);
	}

	private void assertClassUris(SiteConfig sc, String siteUrl,
			String... expectedClassUris) {
		Set<String> expected = new HashSet<>(Arrays.asList(expectedClassUris));
		Set<String> actual = new HashSet<>(sc.getSite(siteUrl).getClassUris());
		assertEquals("classUris", expected, actual);
	}

}
