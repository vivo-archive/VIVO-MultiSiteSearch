package edu.cornell.mannlib.vivo.mms.linkedData;

import static edu.cornell.mannlib.vivo.mms.linkedData.LinkedDataTestUtils.*;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.cornell.mannlib.vivo.mms.utils.Log4JHelper;

public class LinkedDataExpanderTest {

    @BeforeClass
    public static void setUpClass() {
        Log4JHelper.resetToConsole();
    }

    @Test
    public void test() throws Exception{

        Model sourceModel = getBdc34TestModel();
        assertTrue("The testing model created from bdc34.n3 should have "+
                   "some statements" , sourceModel.size() > 0 );
        
        LinkedDataService innerLDService = 
            new ModelLinkedDataService( sourceModel );
        LinkedDataService expandingLDService = 
            new ExpandingLinkedDataService( innerLDService, new UrisToExpand() );
        
        Model expandedModel = ModelFactory.createDefaultModel();
        assertTrue("should be empty", expandedModel.size() == 0 );
        expandingLDService.getLinkedData( URI_BDC34, expandedModel);
        assertTrue("should now have some statements " , expandedModel.size() > 0 );
        
        //Check that there are one and two hop predicates
        assertTrue("should have one hop statements", hasOneHopStatementsForUri(expandedModel,URI_BDC34));
        assertTrue("should have two hop statements", hasTwoHopStatementsForUri(expandedModel,URI_BDC34));
    }

}
