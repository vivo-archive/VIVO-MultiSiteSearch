package edu.cornell.mannlib.vivo.mms.solr;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFService;

public interface DocumentMaker {
    SolrInputDocument makeDocument( String uri, Model data);
}
