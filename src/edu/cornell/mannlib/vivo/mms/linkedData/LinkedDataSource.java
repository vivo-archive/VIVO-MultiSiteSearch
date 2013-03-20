package edu.cornell.mannlib.vivo.mms.linkedData;

import com.hp.hpl.jena.rdf.model.Model;

public interface LinkedDataSource {

    Model getData(String uri, org.apache.hadoop.mapreduce.Mapper.Context context);
    
}
