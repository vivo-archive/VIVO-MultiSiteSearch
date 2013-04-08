package edu.cornell.mannlib.vivo.mss.linkedData;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Interface for classes that can be used to get RDF linked data.
 */
public interface LinkedDataService {
    /**
     * Get the linked data for the URI and add it to Model m. 
     * @throws Exception 
     */
    public void getLinkedData( String uri, Model m ) throws Exception;
}

