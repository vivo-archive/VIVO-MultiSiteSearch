package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Iterables;

import edu.cornell.mannlib.vivo.mms.discovery.largevivosite.DiscoverUrisUsingSearchPages;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker.HttpWorkerException;


public class VivoDiscoverUrisForSite implements DiscoverUrisForSite {

    HttpWorker http;
    List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();
    Collection<String> classes;
    
    public VivoDiscoverUrisForSite( HttpWorker http){
        this.http = http;
    }
    
    public void setClasses(Collection<String> uris){
        classes = uris;
    }
    public Collection<String> getClasses(){
        if( classes != null)
            return classes;
        else
            return defaultClasses();
    }
    
    public Collection<String> defaultClasses(){
       return Arrays.asList(
               "http://blalblabl/classX"
               );
    }
    
    @Override
    public Iterable<String> getUrisForSite(String siteUrl) {         
        for (String classUri : getClasses()) {
            try {
                iterables.add(DiscoverUrisUsingSearchPages.getWorker(siteUrl, classUri, http).discover());
            } catch (HttpWorkerException e) {
                log.error("Failed to discover individuals for class '"
                        + classUri + "' at '" + siteUrl + "'", e);
            }
        }

        return Iterables.concat(iterables);        
    }

    private static final Log log = LogFactory.getLog(VivoDiscoverUrisForSite.class);
}
