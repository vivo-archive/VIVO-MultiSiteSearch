package edu.cornell.mannlib.vivo.mms.linkedData;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ModelLinkedDataServiceTest{
    static String URI_BDC34 = "http://vivo.cornell.edu/individual/individual19589";
    static String bdc34ExampleRdf = "edu/cornell/mannlib/vivo/mms/linkedData/resources/bdc34.n3";

    @Test
    public void test() throws Exception{
         InputStream in = 
            this.getClass().getClassLoader().getResourceAsStream(bdc34ExampleRdf);
        assertNotNull("Could not read in " +bdc34ExampleRdf , in);

        Model sourceModel = ModelFactory.createDefaultModel();
        sourceModel.read( in, null , "N3" );
        assertTrue("The testing model created from bdc34.n3 should have "+
                   "some statements" , sourceModel.size() > 0 );
        
        LinkedDataService ldService = new ModelLinkedDataService( sourceModel );
        
        Model outputModel = ModelFactory.createDefaultModel();
        assertTrue("should be empty", outputModel.size() == 0 );
        ldService.getLinkedData( URI_BDC34, outputModel);
        assertTrue("should now have some statements " , outputModel.size() > 0 );
       

        // Check that we have statements that are one step away from the individual
        // but not two steps away. There are two statements two edges away from
        // URI_BDC34 but the ModelLinkedDataService only returns one step
        // triples when getLinkedData() is called. 

        String queryString = "ASK  { <"+ URI_BDC34 + "> ?p ?o }";
        Query query = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, outputModel) ;
        boolean result = qexec.execAsk() ;
        qexec.close() ;

        assertTrue( result );

        String queryString2 = "ASK  { <"+ URI_BDC34 + "> ?p ?o }";
        query = QueryFactory.create(queryString2);
        qexec = QueryExecutionFactory.create(query, outputModel);
        boolean hadSecondHopPredicates = qexec.execAsk();
        qexec.close();

    }
}
