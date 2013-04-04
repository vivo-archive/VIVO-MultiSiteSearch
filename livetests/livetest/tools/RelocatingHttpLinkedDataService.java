/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package livetest.tools;

import org.apache.http.client.HttpClient;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mms.linkedData.HttpLinkedDataService;

/**
 * TODO
 */
public class RelocatingHttpLinkedDataService extends HttpLinkedDataService {
	private final String defaultNamespace;
	private final String relocatedNamespace;

	public RelocatingHttpLinkedDataService(HttpClient http,
			String defaultNamespace, String siteUrl) {
		super(http);
		this.defaultNamespace = defaultNamespace;
		this.relocatedNamespace = siteUrl + "/individual/";
	}

	@Override
	public void getLinkedData(String uri, Model m) throws Exception {
		if (uri.startsWith(defaultNamespace)) {
			uri = uri.replace(defaultNamespace, relocatedNamespace);
		}

		super.getLinkedData(uri, m);
	}
}
