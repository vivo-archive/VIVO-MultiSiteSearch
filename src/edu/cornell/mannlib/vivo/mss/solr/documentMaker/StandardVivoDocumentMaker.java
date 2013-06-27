/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vivo.mss.solr.documentMaker;

import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.AllTextDM;
import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.BasicFieldsDM;
import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.ClassGroupsDM;
import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.ClassesDM;
import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.CoreClassesDM;
import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.MostSpecificClassesDM;
import edu.cornell.mannlib.vivo.mss.solr.documentmodifier.SiteDataDM;
import edu.cornell.mannlib.vivo.mss.solr.filters.ClassGroupsFilter;
import edu.cornell.mannlib.vivo.mss.solr.filters.CoreClassesFilter;
import edu.cornell.mannlib.vivo.mss.solr.filters.MostSpecificClassesFilter;

/**
 * The Solr documents for a standard VIVO index require these modifiers.
 */
public class StandardVivoDocumentMaker extends BaseDocumentMaker {

	public StandardVivoDocumentMaker(String siteName, String siteUrl) {
		super(new SiteDataDM(siteName, siteUrl), new BasicFieldsDM(),
				new AllTextDM(), new ClassesDM(), new CoreClassesDM(
						new CoreClassesFilter()), new MostSpecificClassesDM(
						new MostSpecificClassesFilter()), new ClassGroupsDM(
						new ClassGroupsFilter()));
	}

}
