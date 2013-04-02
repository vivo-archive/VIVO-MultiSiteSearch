package livetest.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vivo.mms.utils.http.BasicHttpWorker;
import edu.cornell.mannlib.vivo.mms.utils.http.BasicHttpWorkerRequest;
import edu.cornell.mannlib.vivo.mms.utils.http.HttpWorkerException;

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

/**
 * Stop this after just a few HTTP requests.
 */
public class LimitedUseHttpWorker extends BasicHttpWorker {
	private static final Log log = LogFactory
			.getLog(LimitedUseHttpWorker.class);

	private final int maxRequests;
	private int requestsSoFar;

	public LimitedUseHttpWorker(int maxRequests) {
		this.maxRequests = maxRequests;
	}

	@Override
	protected String executeRequest(BasicHttpWorkerRequest<?> request)
			throws HttpWorkerException {
		requestsSoFar++;
		if (requestsSoFar > maxRequests) {
			log.error("TESTING: reached maximum number of requests.");
			return "";
		} else {
			return super.executeRequest(request);
		}
	}
}
