/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package vivo.localhost;

import java.util.Arrays;
import java.util.Collection;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorkerImpl;

/**
 * Hardcoded stuff for testing localhost VIVO
 */
public class DiscoverUrisContextForLocalhost extends DiscoverUrisContext {

	@Override
	public Collection<String> getClassUris(String siteUrl) {
		return Arrays.asList(new String[] { "http://xmlns.com/foaf/0.1/Person",
				"http://vivoweb.org/ontology/core#Continent" });
	}

	@Override
	public HttpWorker getHttpWorker() {
		return new HttpWorkerImpl();
	}

}
