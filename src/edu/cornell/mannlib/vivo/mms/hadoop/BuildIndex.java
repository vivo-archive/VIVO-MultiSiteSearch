package edu.cornell.mannlib.vivo.mms.hadoop;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class BuildIndex  extends Configured implements Tool {
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new BuildIndex(), args);
		System.exit(res);
	}
	
	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2)
			return printUsage();

		String workingDir = args[1];
		Configuration conf = getConf();

		// load the job configuration XML and add to hadoop's configuration
		String configXmlPath = args[0];
		conf.addResource(
				new File(configXmlPath).getAbsoluteFile().toURI().toURL());
		conf.reloadConfiguration();
		checkConfig(conf);

		Path discoveryInput = new Path(workingDir, "discoveryIn");
		Path discoveryOutput = new Path(workingDir, "urisToIndex");
 
		Path indexOutput = new Path(workingDir, "indexed");
		
		Job discoveryJob = new Job(conf);
		discoveryJob.setJobName("BuildIndex.discovery");
		discoveryJob.setMapperClass( UriDiscovery.class);
		
		FileInputFormat.addInputPath(discoveryJob, discoveryInput);
		FileOutputFormat.setOutputPath(discoveryJob, discoveryOutput);
		
		discoveryJob.setOutputKeyClass(Text.class);
		discoveryJob.setOutputValueClass(Text.class);
				
		System.err.println("\nDiscovery job submitted and waiting for completion.\n");
		boolean discoverySuccess = 
				discoveryJob.waitForCompletion(true);
		
		if( discoverySuccess == false){
			System.err.println("Discovery job failed");		
			return 1;
		}else{
			System.err.println("Discovery job succeeded");			
		}
		
		Job indexJob = new Job(conf);
		indexJob.setJobName("BuildIndex.index");
		indexJob.setMapperClass( IndexUris.class);
		
		FileInputFormat.addInputPath(indexJob, discoveryOutput);
		FileOutputFormat.setOutputPath(indexJob, indexOutput);
		
		indexJob.setOutputKeyClass(Text.class);
		indexJob.setOutputValueClass(Text.class);
		
		System.err.println("\nIndexing job submitted. Set to run in background.\n");
		return 0;
	}

	private void checkConfig(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	private int printUsage() {
		System.out.println("usage: ");
		System.out.println("BuildIndex config.xml workingDir [siteURL] \n" );		
		return 1;
	} 
}
