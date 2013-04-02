package edu.cornell.mannlib.vivo.mms.linkedData;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
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
    @Override
	public void getLinkedData(String uri, Model outputModel )throws Exception{
        if( uri == null || outputModel==null ) 
            throw new Error("model and uri must not be null ");
        StmtIterator it = 
            dataModel.listStatements(ResourceFactory.createResource( uri ), (Property)null ,(RDFNode)null );
        outputModel.add(it);
    }


}
