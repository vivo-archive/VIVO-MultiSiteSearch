package edu.cornell.mannlib.vivo.mss.linkedData;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Static utility methods to be used in test classes.
 */
public class LinkedDataTestUtils{
    public static String URI_BDC34 = "http://vivo.cornell.edu/individual/individual19589";
    public static String bdc34ExampleRdf = "edu/cornell/mannlib/vivo/mms/linkedData/resources/bdc34.n3";
    
    public static Model getBdc34TestModel(){
         InputStream in = 
            LinkedDataTestUtils.class.getClassLoader().getResourceAsStream(bdc34ExampleRdf);
        assertNotNull("Could not read in " +bdc34ExampleRdf , in);

        Model sourceModel = ModelFactory.createDefaultModel();
        sourceModel.read( in, null , "N3" );
        assertTrue("The testing model created from bdc34.n3 should have "+
                   "some statements" , sourceModel.size() > 0 );
        return sourceModel;
    }    

    /** Returns true if there are nodes one edge away from the URI */
    public static boolean hasOneHopStatementsForUri( Model m, String uri){
        String queryString = "ASK  { <"+ uri + "> ?p ?o }";
        Query query = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
        boolean result = qexec.execAsk() ;
        qexec.close() ;
        return result;
    }

    /** Returns true if there are nodes two edges away from the URI */
    public static boolean hasTwoHopStatementsForUri( Model m, String uri){
        String queryString2 = "ASK  { <"+ URI_BDC34 + "> ?p ?o . ?o ?p2 ?o2 }";
        Query query = QueryFactory.create(queryString2);
        QueryExecution qexec = QueryExecutionFactory.create(query, m);
        boolean hasSecondHopPredicates = qexec.execAsk();
        qexec.close();
        return hasSecondHopPredicates;
    }
}
