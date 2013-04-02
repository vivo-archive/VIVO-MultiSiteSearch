package edu.cornell.mannlib.vivo.mms.linkedData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * LinkedDataService that requests RDF for the initial URI
 * and will request additional RDF if directed to by the 
 * implementation of UrisToExpand.
 *
 */
public class ExpandingLinkedDataService implements LinkedDataService {
    private LinkedDataService innerLDService;
    private UrisToExpand urisToExpand;

    public ExpandingLinkedDataService(
            LinkedDataService innerLDService,
            UrisToExpand urisToExpand) {
        this.innerLDService = innerLDService;
        this.urisToExpand = urisToExpand;
    }
       
    @Override
    public void getLinkedData(String uri, Model model) throws Exception {
        if( model == null )
            throw new Error("Model should never be null");
                
        innerLDService.getLinkedData(uri,model);
        expandData(uri,model);        
    }

    public void expandData(String uri, Model model) throws Exception {
        //get list of additional URIs to get linked data for
        Map<String, List<String>> map = urisToExpand.getUris( uri, model);
        List<String> oneHop = map.get("oneHop");
        List<String> twoHop = map.get("twoHop");

        logger.trace(String.format("oneHop expansion list for URI %s: %s", uri, oneHop));
        logger.trace(String.format("twoHop expansion list for URI %s: %s", uri, twoHop));

        //oneHop = oneHop.filterNot( innerSkipUri );
        //twoHop = twoHop.filterNot( innerSkipUri );

        singleHopExpansion(model, oneHop, twoHop);
        // logger.trace("model for %s after one hop expansion: %s".format(uri,
        //         modelToString(modelExp)));

        secondHopExpansion(model, twoHop);
        // logger.trace("model for %s after two hop expansion: %s".format(uri,
        //         modelToString(modelExp)));
    }

    public void singleHopExpansion(Model model, List<String>... uriLists) throws Exception {
        Set<String> uriSet = new HashSet<>();
        for (List<String> uris : uriLists) {
            uriSet.addAll(uris);
        }
        for (String uri : uriSet) {
            innerLDService.getLinkedData(uri, model);
        }
    }

    public void secondHopExpansion(Model model, List<String> uris) throws Exception {
        //each of the two hop URIs need to be expanded one more hop
        Set<String> urisFor2ndHop = new HashSet<>();
        for (String uri : urisFor2ndHop) {
            urisFor2ndHop.addAll(getUrisFor2ndExpand(model, uri));
        }
        // logger.trace("list of URIs to expand for 2nd hop for individual %s: %s"
        //         .format(uri, urisFor2ndHop));
        singleHopExpansion(model, new ArrayList<>(urisFor2ndHop));
    }

    public List<String> getUrisFor2ndExpand(Model model, String uri2ndHop) {
        return urisToExpand.getSingleHopUris( uri2ndHop, model);
    }

    public boolean innerSkipUri(String uri) {
        return  notHTTP(uri) || hashNamespace(uri)
                || hasDoubleSlash(uri);
    }

    /** Skip the uri if it isn't http protocol. */
    public boolean notHTTP(String uri) {
        return (uri == null) || (!uri.startsWith("http:"));
    }

    /** Skip if the uri is a hash namespace. */
    public boolean hashNamespace(String uri) {
        return (uri != null) && (uri.contains("#"));
    }

    /** Return true if there is a double slash that insn't part of http:// */
    public boolean hasDoubleSlash(String uri) {
        return (uri.substring(7).contains("//"));
    }

    Log logger = LogFactory.getLog(ExpandingLinkedDataService.class);

 

}
