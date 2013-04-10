package edu.cornell.mannlib.vivo.mss.solr;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

public class BasicDocumentMaker implements DocumentMaker{  

    @Override
    public SolrInputDocument makeDocument(String uri, Model data) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("URI", uri);
        doc.addField("site_name", "BOGUS");
        doc.addField("site_URL", "BOGUS");
        return doc;
    }

}