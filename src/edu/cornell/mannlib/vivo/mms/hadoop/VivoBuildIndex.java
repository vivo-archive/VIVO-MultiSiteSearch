package edu.cornell.mannlib.vivo.mms.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;


/**
 * An index builder for Vivo sites.
 */

public  class VivoBuildIndex extends BuildIndex{

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new BuildIndex(), args);
		System.exit(res);
	}

    @Override
    public Class getIndexClass(){
        return VivoIndexUris.class;
    }

    @Override
    public Class getDiscoveryClass(){
        return VivoUriDiscovery.class;
    }
}
