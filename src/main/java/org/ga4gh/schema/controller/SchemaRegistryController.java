package org.ga4gh.schema.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnastack.ga4gh.dataconnect.model.DataModel;

import lombok.extern.slf4j.Slf4j;

import org.ga4gh.schema.model.NamespaceResponse;
import org.ga4gh.schema.model.ReturnTypesResponse;
import org.ga4gh.schema.model.Schema;
import org.ga4gh.schema.model.SchemaRegistryServiceInfo;
import org.ga4gh.schema.model.SchemaResponse;
import org.ga4gh.schema.model.SchemaVersion;
import org.ga4gh.schema.model.SchemaVersionsResponse;
import org.ga4gh.schema.provider.SchemaManager;
import org.ga4gh.schema.provider.SchemaProvider;

@RestController
@Slf4j
public class SchemaRegistryController {

  private SchemaManager manager;
  

  SchemaRegistryController() {    
    manager = new SchemaManager();
  }


  @GetMapping("/schemas")
  SchemaResponse allSchemas() {
	  List<String> maintainers = Arrays.asList("dbGaP", "U Wash");
	  Schema test = new Schema("phs001554", "v2",  maintainers, "released");
	  List<Schema> schemas = new ArrayList<>();
	  schemas.add(test);
	  SchemaResponse response= new SchemaResponse("dbGaP", schemas);
	  return(response);
  }

  @GetMapping("/namespaces")
  NamespaceResponse all() {
	  NamespaceResponse response= new NamespaceResponse("http://localCatalog", manager.getNamespaces());
    return response;
  }
  
  @GetMapping("/dicts/{namespace}/{schema_name}/versions/{semantic_version}")
  String getXML(@PathVariable String namespace, @PathVariable String schema_name, @PathVariable String semantic_version) {
	  
	  String xmlDict = manager.getProvider(namespace).supplyXML(schema_name);
	  return xmlDict;
			    //return provider.findById(id)
			    //  .orElseThrow(() -> new SchemaNotFoundException(id));
  }
  
  @GetMapping("/schemas/{namespace}/{schema_name}/versions/{semantic_version}")
  DataModel getSchema(@PathVariable String namespace, @PathVariable String schema_name, @PathVariable String semantic_version) {
	  
	  DataModel model = manager.getProvider(namespace).supplySchema(schema_name);
	  return model;
	    //return provider.findById(id)
	    //  .orElseThrow(() -> new SchemaNotFoundException(id));
  }
  
  @GetMapping("/schemas/{namespace}/{schema_name}/versions/latest")
  DataModel getLatestSchema(@PathVariable String namespace, @PathVariable String schema_name) {
	  
	  DataModel model = manager.getProvider(namespace).supplySchema(schema_name);
	  return model;
	    //return provider.findById(id)
	    //  .orElseThrow(() -> new SchemaNotFoundException(id));
  }
  
  @GetMapping("/dictionaries/{schema_name}/{schema_version}")
  SchemaResponse dictSchemas(@PathVariable String namespace,
		  @PathVariable String schema_name,
		  @PathVariable String schema_version
		  ) {
    
     log.debug(schema_name + " " + schema_version);
     List<Schema> schemas = manager.getProvider(namespace).getSchemas(schema_name + "/" + schema_version);
     SchemaResponse response= new SchemaResponse("dbGaP", schemas);
     return(response);
  }
  
  @GetMapping("/schemas/{namespace}")

  SchemaResponse getSchemas(@PathVariable String namespace,
		  @RequestParam(required = false) String study,
		  @RequestParam(required = false) String study_version,
		  @RequestParam(required = false) String lifecycle_stage
		  ) {
    
     log.debug(study + " " + study_version);
     log.debug("namespace is  " + namespace);
     SchemaProvider provider = manager.getProvider(namespace);
     log.debug("schema provider class  is  " + provider.getClass().getName());
     String studyString = "";
     if (study != null) {
    	 studyString = study + "/" + study + "." + study_version;
     }
     List<Schema> schemas = provider.getSchemas(studyString);
	 SchemaResponse response= new SchemaResponse(namespace, schemas);
	 return(response);
  }


	@GetMapping("/schemas/{namespace}/{schema_name}/versions/")
	SchemaVersionsResponse getSchemaVersions(@PathVariable String namespace, @PathVariable String schema_name) {
	  
	   log.debug("namespace is  " + namespace);
	   SchemaProvider provider = manager.getProvider(namespace);
	   log.debug("schema provider class  is  " + provider.getClass().getName());
	   String studyString = "";
	   List<SchemaVersion> versions = provider.getSchemaVersions(studyString);
		 SchemaVersionsResponse response= new SchemaVersionsResponse(schema_name, versions);
		 return(response);
	}
	
	@GetMapping("/schematypes/{namespace}")
	ReturnTypesResponse getReturnTypes(@PathVariable String namespace) {
	  
	   SchemaProvider provider = manager.getProvider(namespace);
	   List<String> types = provider.getReturnTypes();
	   ReturnTypesResponse response= new ReturnTypesResponse(namespace, types);
		return(response);
	}
	
    @GetMapping("/service-info")
    public org.ga4gh.schema.model.SchemaRegistryServiceInfo getServiceInfo() {
    	SchemaRegistryServiceInfo serviceInfo = new SchemaRegistryServiceInfo();
    	return serviceInfo;
    }
}
