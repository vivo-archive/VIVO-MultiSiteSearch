/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package testInHadoop;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.ToolRunner;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mss.hadoop.BaseBuildIndex;
import edu.cornell.mannlib.vivo.mss.hadoop.BaseIndexUris;
import edu.cornell.mannlib.vivo.mss.hadoop.BaseUriDiscovery;

/**
 * TODO
 */
public class DummyBuildIndex extends BaseBuildIndex {
	public static void main(String[] args) throws Exception {
		DummyBuildIndex indexer = new DummyBuildIndex();
		int res = ToolRunner.run(new Configuration(), indexer, args);
		System.exit(res);
	}

	@Override
	public Class<? extends Mapper<LongWritable, Text, Text, Text>> getDiscoveryClass() {
		return DummyDiscovery.class;
	}

	@Override
	public Class<? extends Mapper<LongWritable, Text, Text, Text>> getIndexClass() {
		return DummyIndexer.class;
	}
	
	public static class DummyDiscovery extends BaseUriDiscovery {
		public DummyDiscovery() {
			super(new DiscoverHardcodedUris());
		}
	}
	
	public static class DiscoverHardcodedUris implements DiscoveryWorker {
		@Override
		public Iterable<String> getUrisForSite(String siteUrl) {
			return Arrays.asList(
					"http://vivo.cornell.edu/individual/individual19589",
					"http://vivo.cornell.edu/individual/JamesBlake",
					"http://vivo.cornell.edu/individual/individual24416",
					"http://vivo.cornell.edu/individual/individual5320",
					"http://vivo.cornell.edu/individual/individual6059");
		}

	}

	public static class DummyIndexer extends BaseIndexUris {
		@Override
		protected void setupLinkedDataSource(Context context) {
			System.out
					.println("BaseIndexUris.setupLinkedDataSource() not implemented.");
		}

		@Override
		protected void indexToSolr(SolrInputDocument doc)
				throws SolrServerException, IOException {
			System.out.println("DummyIndexer.indexToSolr() not implemented.");
		}

		@Override
		protected SolrInputDocument makeDocument(String uri, Model data) {
			System.out.println("DummyIndexer.makeDocument() not implemented.");
			return null;
		}

		@Override
		protected Model getLinkedData(String uri) throws Exception {
			System.out.println("DummyIndexer.getLinkedData() not implemented.");
			return null;
		}

		@Override
		protected void setupSolrServer(Context context) {
			System.out.println("DummyIndexer.setupSolrServer() not implemented.");
		}

	}

}
