/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.utils.monitoring;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 */
public class ElapsedTime {

	public static final ElapsedTime linkedData = new ElapsedTime("Linked Data");

	public static final ElapsedTime solr = new ElapsedTime("Solr");

	private final String name;

	private final ThreadLocal<Long> startTime = new ThreadLocal<>();

	private final AtomicLong totalTime = new AtomicLong(0L);

	public ElapsedTime(String name) {
		this.name = name;
	}

	public void start() {
		startTime.set(System.currentTimeMillis());
	}

	public void end() {
		totalTime.addAndGet(System.currentTimeMillis() - startTime.get());
	}

	public String getName() {
		return name;
	}

	public long getTotal() {
		return totalTime.get();
	}

	@Override
	public String toString() {
		return "Elapsed time for '" + name + "' = " + (getTotal() / 1000L)
				+ " seconds.";
	}

}
