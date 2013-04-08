package edu.cornell.mannlib.vivo.mss.hadoop;

import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorker;

/*
 * This is a suggestion of how something like this could be done.
 * This subclass of BaseUriDiscovery gets the info it needs
 * out of the haddop Context and constructs any objects it needs.
 * 
 * When doing this think "simple".  When you wonder how do I get
 * the list of ISO-2332 formats to the ISO2332HttpResponseTransformer
 * try the following in this order: 
 * 1. as defaults in the class ISO2332HttpResponseTransformer 
 * 2. via the constructor to the class ISO2332HttpResponseTransformer 
 * 3. via setters to ISO2332HttpResponseTransformer
 * 4. figure out why you did like 1-3 and do a check to see if you are
 *    going down some unecessary customization rabbit hole.
 */

public class VivoUriDiscovery extends BaseUriDiscovery {
	public VivoUriDiscovery(DiscoveryWorker uriSource) {
		super(uriSource);
	}

	// TODO Implement it or get rid of it.
	
	// protected DiscoverUrisForSite getDiscoverUrisForSite( Context context ){
	//
	// String transformLevel =
	// context.getConfig().get(BuildIndexUtils.Iso2332TransformLevel);
	//
	// return new VivoDiscoverUrisForSite(
	// new HttpWorkerImpl( HdfsHttpClientCacheStorage( context.getFs() ),
	// new DiscoverUrisUsingSearchPages( ),
	// new ISO2332HttpResponseTransformer( transformLevel ) ,
	// );
	//
	// throw new Error("This is unimplemented.");
	// }
	//
}
