/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mms.discovery;

import org.apache.hadoop.mapreduce.Mapper;

/**
 * TODO
 */
public class DiscoverUrisContextImpl extends DiscoverUrisContext {
	private final Mapper<?, ?, ?, ?>.Context context;

	public DiscoverUrisContextImpl(Mapper<?, ?, ?, ?>.Context context) {
		this.context = context;
	}

}
