/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package testInHadoop;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.ToolRunner;

import edu.cornell.mannlib.vivo.mss.discovery.DiscoveryWorker;
import edu.cornell.mannlib.vivo.mss.hadoop.BaseBuildIndex;
import edu.cornell.mannlib.vivo.mss.hadoop.BaseIndexUris;
import edu.cornell.mannlib.vivo.mss.hadoop.BaseUriDiscovery;
import edu.cornell.mannlib.vivo.mss.linkedData.LinkedDataService;
import edu.cornell.mannlib.vivo.mss.solr.DocumentMaker;
import edu.cornell.mannlib.vivo.mss.solr.SolrIndexService;

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
		protected LinkedDataService setupLinkedDataSource(Context context) {
			throw new RuntimeException(
					"BaseIndexUris.setupLinkedDataSource() not implemented.");
		}

		@Override
		protected DocumentMaker setupDocMaker(Context context) {
			throw new RuntimeException(
					"BaseIndexUris.setupDocMaker() not implemented.");
		}

		@Override
		protected SolrIndexService setupSolrServer(Context context) {
			throw new RuntimeException(
					"BaseIndexUris.setupSolrServer() not implemented.");
		}

	}
}
