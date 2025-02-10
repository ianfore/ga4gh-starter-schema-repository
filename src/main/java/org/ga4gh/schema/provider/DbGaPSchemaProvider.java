package org.ga4gh.schema.provider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


import com.dnastack.ga4gh.dataconnect.model.ColumnSchema;
import com.dnastack.ga4gh.dataconnect.model.DataModel;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;


import org.apache.commons.net.ftp.FTPClient; 
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.ga4gh.schema.dbgap.dict.*;
import org.ga4gh.schema.model.Schema;  

@Slf4j
//@Configuration
//@ConfigurationProperties("app.datadict-datamodel-supplier")
//public class SchemaRepository implements DataModelSupplier {
public class DbGaPSchemaProvider implements SchemaProvider {
	
    private final String base = "https://ftp.ncbi.nlm.nih.gov";
    private final String ftpbase = "ftp.ncbi.nlm.nih.gov";
    private FTPClient ftp;
    
    private final String idPrefix = "dbgap:";
    
    //private Map<String, String> dictMap;
    
    private Pattern pattern;
    
    
    public DbGaPSchemaProvider() {
    	//this.dictMap = getModelMap(); 
    	this.pattern = Pattern.compile(".*\\.(pht\\d*\\.v\\d*)\\..*");
    	
    	// don't connect to FTP until needed, but instantiate a client
    	ftp = new FTPClient();;
    }
    
    // get map of table names to model URLs. The map is in /models
    private Map<String, String>  getModelMap() {

    	ObjectMapper mapper = new ObjectMapper();

        
        String map_file = "/models/data_dict_map.json";
        try {
        	BufferedReader br = new BufferedReader(
   			     new FileReader(map_file));
    		Map<String, String> mapObject = mapper.readValue(br, 
    				new TypeReference<Map<String, String>>() {
    		});
    		return mapObject;
    		
    	} catch (JsonGenerationException e) {
    		e.printStackTrace();
    	} catch (JsonMappingException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;

    }

    private BufferedReader getDictReader(String tableName) {
    	log.info("Retrieving schema for " + tableName);
    	DataDict dict = new DataDict(tableName);
    	log.debug(dict.getPht());
    	String pht_name = dict.getPht();
    	log.info("pht is " + pht_name );
    	log.info("looking for version folder for " + dict.getStudy() + "." + dict.getStudyVersion() );
    	String folder = findFolder(dict.getStudy() + "." + dict.getStudyVersion() );
    	String dictPath = folder + tableName + ".data_dict.xml";
    	
    	log.info("Schema path is " + dictPath);
    	BufferedReader reader = null;
    	try{
            URL url = new URL(this.base + dictPath);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
          }catch(Exception ex){
            System.out.println(ex);
          }
        return reader;
    }
    
    private String getTableId(String dict_path) {
        Matcher matcher = this.pattern.matcher(dict_path);
        if (matcher.find()) {
        	return (this.idPrefix + matcher.group(1));
        }
        else {
        	return dict_path;
        }
    }


    @Override
	public String supplyXML(String tableName) {
        try {
        	BufferedReader reader = this.getDictReader(tableName);
        	StringBuilder stringBuilder = new StringBuilder();
        	String line = null;
        	String ls = System.getProperty("line.separator");
        	while ((line = reader.readLine()) != null) {
        		stringBuilder.append(line);
        		stringBuilder.append(ls);
        	}
        	// delete the last new line separator
        	stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        	reader.close();

        	return stringBuilder.toString();
        } catch (Exception ex) {
        	log.error("Failed to load DataModel for {}", tableName, ex);
        	return null;
        }
    	
    }
    	   
    @Override
	public DataModel supplySchema(String tableName) {        
        	   
        try {
        	BufferedReader br = this.getDictReader(tableName);
        	JAXBContext jaxbContext 	= JAXBContext.newInstance( DataTable.class );
        	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        	DataTable dt = (DataTable) jaxbUnmarshaller.unmarshal( br );        	
        	//DataTable dt = JAXB.unmarshal(br, DataTable.class);
        	
        	DataModel dm = new DataModel();
        	dm.setAdditionalProperty("description", dt.getDescription());
        	dm.setAdditionalProperty("$id", this.getTableId(tableName));
        	
        	List<Variable> variables = dt.getVariable();
            Iterator<Variable> it = variables.iterator();
            
            Map<String, ColumnSchema> properties = new HashMap<String, ColumnSchema>();
            
            while (it.hasNext()) {
            	Variable var = it.next();

            	ColumnSchema col = new ColumnSchema();
            	col.setAdditionalProperty("$id", this.idPrefix + var.getId());
            	col.setAdditionalProperty("description", var.getDescription());
            	col.setAdditionalProperty("type", var.getType());
            	col.setAdditionalProperty("$unit", var.getUnit());

            	//enumerated values
            	List<org.ga4gh.schema.dbgap.dict.Value> values = var.getValue();
            	if (values.size() > 0 ) { 
            		Iterator<Value> valueIterator = values.iterator();

            		List<Object> valList = new ArrayList<Object>();
            		while (valueIterator.hasNext()) {
            			Value val = valueIterator.next();
            			Map<String, String> valDetails = new HashMap<String, String>();
            			valDetails.put("const",val.getCode());
            			valDetails.put("title",val.getContent());
            			valList.add(valDetails);
            		}

            		col.setAdditionalProperty("oneOf", valList);
            	}

            	properties.put(var.getName(), col);
            }
            
        	dm.setAdditionalProperty("properties", properties);
        	return dm;

        } catch (Exception ex) {
        	log.error("Failed to load or convert DataModel for {}", tableName, ex);
        	return null;
        }
    }
    
    
    public String findById(String tableName) {
    	return "Hello World!";
    }
    
    //@Override
	public List<DataDict> getDictionaries(String studyVersion) 
    { 
    	List<DataDict> dicts = new ArrayList<>();
    	try {
    		if  (!ftp.isConnected()) getFTPClient();
    		if (this.ftp.isConnected()) { 
                String studypath = "/dbgap/studies/" + studyVersion + "/pheno_variable_summaries/";
                log.debug("looking for files in " + studypath);
                String[] filesFTP = ftp.listNames(studypath);
                log.debug("found file count: " + filesFTP.length);
                for (String filePath : filesFTP) { 
                    if (filePath.endsWith("data_dict.xml")) {
                    	String[] parts = filePath.split("/");
                    	String phtString = parts[parts.length - 1];
                    	phtString = phtString.replace(".data_dict.xml", "");
                    	log.debug("found dictionary: " + phtString);
                    	DataDict dict = new DataDict(phtString);
                    	dicts.add(dict);
                    }
                } 
            } 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
        }
        return dicts;
    }

	@Override
	public List<Schema> getSchemas(String studyVersion) {

		List<Schema> schemas = new ArrayList<>();
		List<DataDict> dictionaries = getDictionaries(studyVersion);
		List<String> maintainers = Arrays.asList("dbGaP");
		for (DataDict dict : dictionaries) {
			 schemas.add(new Schema(dict.getDictFullName(), dict.getVersion(),  maintainers, "released"));
		  }
		return schemas;
	}
	  

    private void getFTPClient() {

    	// Create an instance of FTPClient 
        ftp = new FTPClient(); 
        try { 
        	
        	//log.info("setting up ftp. log level is " + env.getProperty("logging.level.root"));
        	// Establish a connection with the FTP URL 
            //if (env.getProperty("logging.level.root") == "DEBUG") {
            	//ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            //}
            ftp.connect(ftpbase); 
            //ftp.connect(ftpbase, port);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new IOException("Exception in connecting to FTP Server");
            }
            // Needed for Docker/GCP container
            ftp.enterLocalPassiveMode();
            
            // Enter user details : user name and password 
            boolean isSuccess = ftp.login("anonymous", "schemarepo@gmail.com"); 
        }
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
    }
    
    private String findFolder(String studyVersion) {
        String study = studyVersion.split("\\.")[0];
        String studypath = "/dbgap/studies/" + study;
        String dirName = "";
        try { 
       		if  (!this.ftp.isConnected()) getFTPClient();
       		log.info("looking for version folders in " + studypath);
	        FTPFile[] dirsFTP = ftp.listDirectories(studypath);
	        log.info("found file count: " + dirsFTP.length);
	        for (FTPFile dir : dirsFTP) { 
	            dirName = dir.getName();
	            log.info("found directory : " + dirName);
	            if (dirName.startsWith(studyVersion)) break;
	        }
        }
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
        	
        }
        return studypath + "/" + dirName + "/pheno_variable_summaries/"; 
    }
} 
