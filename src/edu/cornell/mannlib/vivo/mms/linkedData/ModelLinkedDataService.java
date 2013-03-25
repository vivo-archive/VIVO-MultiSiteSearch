package edu.cornell.mannlib.vivo.mms.linkedData;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * Jena Model based LinkedDataService that gets all the 
 * SPO statements for the URI. */
public class ModelLinkedDataService implements LinkedDataService{
    Model dataModel;
    
    public ModelLinkedDataService(Model model){
        this.dataModel = model;
    }

    /** Adds just the SubPrdObj statements for the URI. */
    public void getLinkedData(String uri, Model m )throws Exception{
        if( uri == null || m==null ) 
            throw new Error("model and uri must not be null ");

        StmtIterator it = 
            m.listStatements(ResourceFactory.createResource( uri ), null ,null );
        m.add(StmtIterator arg0);
    }


}
