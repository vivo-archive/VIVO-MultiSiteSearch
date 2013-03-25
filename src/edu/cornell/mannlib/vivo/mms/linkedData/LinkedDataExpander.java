package edu.cornell.mannlib.vivo.mms.linkedData;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;
import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFService;

/**
 Interface for getting the RDF for a given URI.
*/
public interface LinkedDataExpander {

    /** Returns the RDF needed to make a  solr document for a given URI. 
     * @throws Exception */
    RDFService getData(String uri,  Configuration configuration) throws Exception;    
    
}
