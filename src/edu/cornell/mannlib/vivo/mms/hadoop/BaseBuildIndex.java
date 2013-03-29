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


public abstract class BaseBuildIndex extends Configured implements Tool {
	
	
	@SuppressWarnings("unchecked")
    @Override
	public int run(String[] args) throws Exception {
		if (args.length != 2)
			return printUsage();
			
		// load the job configuration XML and add to hadoop's configuration
		Configuration conf = getConf();
		String configXmlPath = args[0];
		conf.addResource(
				new File(configXmlPath).getAbsoluteFile().toURI().toURL());
		conf.reloadConfiguration();

		String workingDir = args[1];
        conf.set(BuildIndexUtils.workingDir, workingDir);

		//could have a method like checkConfig(conf);

		Path discoveryInput = new Path(workingDir, "discoveryIn");
		Path discoveryOutput = new Path(workingDir, "urisToIndex");
 
		Path indexOutput = new Path(workingDir, "indexed");
		
		//set up the URI discovery job for the site
		Job discoveryJob = new Job(conf);
		discoveryJob.setJobName("BuildIndex.discovery");
		discoveryJob.setMapperClass( getDiscoveryClass() );
		discoveryJob.setNumReduceTasks(0);
		discoveryJob.setJarByClass( getDiscoveryClass() );
		
		FileInputFormat.addInputPath(discoveryJob, discoveryInput);
		FileOutputFormat.setOutputPath(discoveryJob, discoveryOutput);
		
		discoveryJob.setOutputKeyClass(Text.class);
		discoveryJob.setOutputValueClass(Text.class);
				
		discoveryJob.setNumReduceTasks(0);
		System.err.println("\nDiscovery job submitted and waiting for completion.\n");
		boolean discoverySuccess = 
				discoveryJob.waitForCompletion(true);
		
		if( discoverySuccess == false){
			System.err.println("Discovery job failed");		
			return 1;
		}else{
			System.err.println("Discovery job succeeded");			
		}
		
		// set up the Indexing job for the URIs from discovery
		Job indexJob = new Job(conf);
		indexJob.setJobName("BuildIndex.index");
		indexJob.setMapperClass( getIndexClass() );
		indexJob.setJarByClass( getIndexClass() );
		
		FileInputFormat.addInputPath(indexJob, discoveryOutput);
		FileOutputFormat.setOutputPath(indexJob, indexOutput);
		
		indexJob.setOutputKeyClass(Text.class);
		indexJob.setOutputValueClass(Text.class);
		
		System.err.println("\nIndexing job submitted and waiting for completion.\n");
		boolean indexSuccess = 
                indexJob.waitForCompletion(true);
		
		if( indexSuccess == false){
            System.err.println("Indexing job failed");     
            return 1;
        }else{
            System.err.println("Indexing job succeeded");
            return 0;
        }		
	}

	@SuppressWarnings("rawtypes")
    public abstract Class getDiscoveryClass();

	
	@SuppressWarnings("rawtypes")
    public abstract Class getIndexClass();
	

	private int printUsage() {
		System.out.println("usage: ");
		System.out.println("BuildIndex config.xml workingDir [siteURL] \n" );		
		return 1;
	} 
}
