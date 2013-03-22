/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * TODO
 */
public class XPathHelper {
	public static final XmlPrefix RDF_PREFIX = new XmlPrefix("rdf",
			"http://www.w3.org/1999/02/22-rdf-syntax-ns#");

	// ----------------------------------------------------------------------
	// The factory
	// ----------------------------------------------------------------------

	/**
	 * Create a helper instance with an optional list of prefixes.
	 */
	public static XPathHelper getHelper(XmlPrefix... xmlPrefixes) {
		return new XPathHelper(xmlPrefixes);
	}

	// ----------------------------------------------------------------------
	// The instance
	// ----------------------------------------------------------------------

	private final XPath xpath;

	public XPathHelper(XmlPrefix[] xmlPrefixes) {
		this.xpath = XPathFactory.newInstance().newXPath();
		this.xpath.setNamespaceContext(new XmlNamespaceContext(xmlPrefixes));
	}

	/**
	 * Search for an Xpath pattern in the context of a node, returning a handy
	 * list.
	 */
	public List<Node> findNodes(String pattern, Node context) {
		try {
			XPathExpression xpe = xpath.compile(pattern);
			NodeList nodes = (NodeList) xpe.evaluate(context,
					XPathConstants.NODESET);
			List<Node> list = new ArrayList<Node>();
			for (int i = 0; i < nodes.getLength(); i++) {
				list.add(nodes.item(i));
			}
			return list;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

}
