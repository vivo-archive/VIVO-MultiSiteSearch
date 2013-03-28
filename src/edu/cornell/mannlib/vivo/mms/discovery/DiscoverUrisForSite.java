package edu.cornell.mannlib.vivo.mms.discovery;

public interface DiscoverUrisForSite {	
    Iterable<String> getUrisForSite(String siteUrl); 
}
