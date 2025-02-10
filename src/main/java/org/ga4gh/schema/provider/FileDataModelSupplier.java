package org.ga4gh.schema.provider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ga4gh.schema.model.Schema;

import com.dnastack.ga4gh.dataconnect.DataModelSupplier;
import com.dnastack.ga4gh.dataconnect.model.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
//@ConfigurationProperties("app.file-datamodel-supplier")
public class FileDataModelSupplier implements DataModelSupplier, SchemaProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private File directory;
    
    public FileDataModelSupplier(String modelRoot) {
    	directory = new File(modelRoot);
    }

    @Override
    public DataModel supply(String tableName) {        
   
    	String model_file = directory + "/" + tableName + ".data_dict.json";
        try {
        	BufferedReader br = new BufferedReader(
        			     new FileReader(model_file));
        	DataModel dm = objectMapper.readValue(br, DataModel.class);
        	return dm;

        } catch (Exception ex) {
        	log.error("Failed to load or convert DataModel for {}", tableName, ex);
        	return null;
        }
    }
    
    @Override
    public List<Schema> getSchemas(String studyVersion) 
    { 
    	List<Schema> schemas = new ArrayList<>();
    	
    	//log.info("file object name is: " + directory.getName()); 
    	if (directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();
            if (files == null) log.info("files was null ");
            if (files != null) {
                for (String file : files) {
                	if (file.endsWith("data_dict.json")) {
                    	String schemaName = file.replace(".data_dict.json", "");                		
                    	log.debug("found schema: " + file);
                    	Schema dict = new Schema(schemaName, "v1", Arrays.asList("local") , "released");
                    	schemas.add(dict);
                    }
                }
            } else {
                System.err.println("Error listing files in the directory.");
            }
        } else {
            System.err.println("Directory does not exist: " + directory.getAbsolutePath());
        }
        return schemas;
    }

	@Override
	public String supplyXML(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataModel supplySchema(String tableName) {
		
		String fileName = tableName + ".data_dict.json";
		log.debug("looking for "+ fileName);
		DataModel model = null;
		File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                	log.debug("File found at " + file.getAbsolutePath());
                	try {
						model = objectMapper.readValue(file, DataModel.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }           	
            }
        }
        return model;
	}
}
