package edu.cornell.mannlib.vivo.mss.solr;

import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

public interface DocumentMaker {
    SolrInputDocument makeDocument( String uri, Model data) throws DocumentMakerException;
}
