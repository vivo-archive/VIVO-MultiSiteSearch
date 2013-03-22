package edu.cornell.mannlib.vivo.mms.solr;

import org.apache.solr.common.SolrInputDocument;

import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFService;

public class DocumentMakerImpl implements DocumentMaker{  

    @Override
    public SolrInputDocument makeDocument(String uri, RDFService data) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("uri", uri);
        return doc;
    }

}
