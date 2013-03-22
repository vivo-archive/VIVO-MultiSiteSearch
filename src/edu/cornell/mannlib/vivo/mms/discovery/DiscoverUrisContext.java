/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.Collection;

import org.apache.hadoop.mapreduce.Mapper;

import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;

/**
 * All the context you need to know if you are implementing the
 * DiscoverUrisForSite interface.
 */
public abstract class DiscoverUrisContext {

	public static DiscoverUrisContext wrap(Mapper<?, ?, ?, ?>.Context context) {
		return new DiscoverUrisContextImpl(context);
	}

	public abstract Collection<String> getClassUris(String siteUrl);

	public abstract HttpWorker getHttpWorker();
}