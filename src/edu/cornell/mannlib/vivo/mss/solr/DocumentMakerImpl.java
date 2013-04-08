package edu.cornell.mannlib.vivo.mss.solr;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

public class DocumentMakerImpl implements DocumentMaker{  

    @Override
    public SolrInputDocument makeDocument(String uri, Model data) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("uri", uri);
        return doc;
    }

}
