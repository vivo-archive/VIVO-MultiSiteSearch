/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.Collection;

import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;

import edu.cornell.mannlib.vivo.mms.configuration.SiteConfig.SiteConfigException;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorker;

/**
 * All the context you need to know if you are implementing the
 * DiscoverUrisForSite interface.
 */
public abstract class DiscoverUrisContext {

	public static DiscoverUrisContext wrap(Mapper<?, ?, ?, ?>.Context context,
			Document siteConfigDoc) throws SiteConfigException {
		return new DiscoverUrisContextImpl(context, siteConfigDoc);
	}

	public abstract Collection<String> getClassUris(String siteUrl);

	public abstract HttpWorker getHttpWorker();
}