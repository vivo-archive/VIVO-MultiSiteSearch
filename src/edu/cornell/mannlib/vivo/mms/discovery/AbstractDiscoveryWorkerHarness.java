/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Iterables;

/**
 * The framework for an implementation of DiscoverUrisForSite.
 */
public abstract class AbstractDiscoveryWorkerHarness implements
		DiscoverUrisForSite {

	/**
	 * Discover individual URIs at this site for each classURI in the discovery
	 * context.
	 */
	@Override
	public final Iterable<String> getUrisForSite(String siteUrl,
			DiscoverUrisContext duContext) {
		List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();

		Collection<String> classUris = duContext.getClassUris(siteUrl);
		for (String classUri : classUris) {
			iterables.add(getWorker(siteUrl, classUri, duContext).discover());
		}

		return Iterables.concat(iterables);
	}

	/**
	 * Get a worker that will obtain the individual URIs for this class at this
	 * site.
	 */
	protected abstract DiscoveryWorker getWorker(String siteUrl,
			String classUri, DiscoverUrisContext duContext);

	/**
	 * The worker looks like this.
	 */
	public static abstract class DiscoveryWorker {
		protected final String siteUrl;
		protected final String classUri;
		protected final DiscoverUrisContext duContext;

		public DiscoveryWorker(String siteUrl, String classUri,
				DiscoverUrisContext duContext) {
			this.siteUrl = siteUrl;
			this.classUri = classUri;
			this.duContext = duContext;
		}

		public abstract Iterable<String> discover();
	}

}
