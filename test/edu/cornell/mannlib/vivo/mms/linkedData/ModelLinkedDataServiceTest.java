package edu.cornell.mannlib.vivo.mms.linkedData;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import static edu.cornell.mannlib.vivo.mms.linkedData.LinkedDataTestUtils.*;

public class ModelLinkedDataServiceTest{

    @Test
    public void test() throws Exception{

        Model sourceModel = getBdc34TestModel();
        assertTrue("The testing model created from bdc34.n3 should have "+
                   "some statements" , sourceModel.size() > 0 );


        
        // check that one and two hop tests work
        assertTrue( hasOneHopStatementsForUri( sourceModel, URI_BDC34));
        assertTrue( hasTwoHopStatementsForUri( sourceModel, URI_BDC34));

        
        LinkedDataService ldService = new ModelLinkedDataService( sourceModel );
        
        Model model = ModelFactory.createDefaultModel();
        assertTrue("should be empty", model.size() == 0 );
        ldService.getLinkedData( URI_BDC34, model);
        assertTrue("should now have some statements " , model.size() > 0 );
       

        // Check that we have statements that are one step away from the individual
        // but not two steps away. There are two statements two edges away from
        // URI_BDC34 but the ModelLinkedDataService only returns one step
        // triples when getLinkedData() is called. This is the desired behavior
        // since the ModelLinkedDataService is intended to test the 
        // linked data expander. 

        
        assertTrue( hasOneHopStatementsForUri( model, URI_BDC34) );


        // check that there are no two hop predicates
        assertTrue( ! hasTwoHopStatementsForUri(model, URI_BDC34) );

    }
}
