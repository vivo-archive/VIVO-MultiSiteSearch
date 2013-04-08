/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.discovery;

/**
 * A class that does URI discovery.
 */
public interface DiscoveryWorker {
	Iterable<String> getUrisForSite(String siteUrl)
			throws DiscoveryWorkerException;
}
