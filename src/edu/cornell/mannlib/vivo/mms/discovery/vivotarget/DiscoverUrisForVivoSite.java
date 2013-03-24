/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery.vivotarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;

import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext;
import edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisForSite;

/**
 * TODO
 */
public class DiscoverUrisForVivoSite implements DiscoverUrisForSite {

	@Override
	public Iterable<String> getUrisForSite(String siteUrl,
			DiscoverUrisContext duContext) {
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();

		for (String classUri : duContext.getClassUris(siteUrl)) {
			iterators.add(discoverUrisForClass(siteUrl, classUri, duContext)
					.iterator());
		}

		return new Results<String>(iterators);
	}

	/**
	 * Get the individual URIs of this class from this site.
	 */
	private Iterable<String> discoverUrisForClass(String siteUrl,
			String classUri, DiscoverUrisContext duContext) {
		return new UrlsForClassForVivoSite(siteUrl, classUri, duContext)
				.getUris();
	}

	/**
	 * Combine a collection of Iterators into one Iterable.
	 */
	private static class Results<T> implements Iterable<T> {
		private final Collection<Iterator<T>> iterators;

		public Results(Collection<Iterator<T>> iterators) {
			this.iterators = iterators;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Iterator<T> iterator() {
			return IteratorUtils.chainedIterator(iterators);
		}

	}
}
