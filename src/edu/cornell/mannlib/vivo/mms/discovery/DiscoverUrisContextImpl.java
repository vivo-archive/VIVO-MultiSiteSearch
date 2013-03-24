/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;

import edu.cornell.mannlib.vivo.mms.configuration.SiteConfig;
import edu.cornell.mannlib.vivo.mms.configuration.SiteConfig.Site;
import edu.cornell.mannlib.vivo.mms.configuration.SiteConfig.SiteConfigException;
import edu.cornell.mannlib.vivo.mms.utils.HttpWorker;

/**
 * TODO
 */
public class DiscoverUrisContextImpl extends DiscoverUrisContext {
	private static final Log log = LogFactory
			.getLog(DiscoverUrisContextImpl.class);

	private final Mapper<?, ?, ?, ?>.Context context;
	private final SiteConfig siteConfig;

	public DiscoverUrisContextImpl(Mapper<?, ?, ?, ?>.Context context,
			Document siteConfigDoc) throws SiteConfigException {
		this.context = context;
		this.siteConfig = SiteConfig.parse(siteConfigDoc);

		log.debug("Site Config is: " + siteConfig);
	}

	@Override
	public Collection<String> getClassUris(String siteUrl) {
		Site site = siteConfig.getSite(siteUrl);
		if (site == null) {
			return Collections.emptySet();
		} else {
			return site.getClassUris();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cornell.mannlib.vivo.mms.discovery.DiscoverUrisContext#getHttpWorker
	 * ()
	 */
	@Override
	public HttpWorker getHttpWorker() {
		// TODO Auto-generated method stub
		throw new RuntimeException(
				"DiscoverUrisContext.getHttpWorker() not implemented.");
	}

}
