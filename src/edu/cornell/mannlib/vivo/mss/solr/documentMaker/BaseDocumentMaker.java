package edu.cornell.mannlib.vivo.mss.solr.documentMaker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.DocumentModifier;

public abstract class BaseDocumentMaker implements DocumentMaker {
	private static final Log log = LogFactory.getLog(BaseDocumentMaker.class);
	
	private final DocumentModifier[] modifiers;

	protected BaseDocumentMaker(DocumentModifier... modifiers) {
		this.modifiers = modifiers;
	}

	@Override
	public SolrInputDocument makeDocument(String uri, Model m) throws DocumentMakerException {
		if (log.isDebugEnabled()) {
			m.write(System.out, "N3");
		}

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("URI", uri);

		for (DocumentModifier modifier: modifiers) {
			modifier.modifyDocument(doc, uri, m);
		}
		
		return doc;
	}

}
