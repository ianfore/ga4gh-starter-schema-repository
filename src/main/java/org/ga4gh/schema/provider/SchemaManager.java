package org.ga4gh.schema.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ga4gh.schema.model.Namespace;

public class SchemaManager {
	  private Map<String, SchemaProvider> providers;
	  private List<Namespace> namespaces;

	  public SchemaManager() {
	    
	    providers = new HashMap<>();
	    namespaces = new ArrayList<>();
	    
		Namespace ns1 = new Namespace("dbGaP", "https://ncbi.nlm.gov", "1.0.0");
		providers.put(ns1.getNamespaceName(), new DbGaPSchemaProvider());
		
		//Namespace ns2 = new Namespace("gks-core", "https://github.com/ga4gh/gks-core", "1.0.0");
		//providers.put(ns2.getNamespaceName(), new GitHubSchemaProvider("https://github.com/ga4gh/gks-core", null));
		
		Namespace ns3 = new Namespace("dataconnect-demo", "https://localhost", "1.0.0");
		providers.put(ns3.getNamespaceName(), new FileDataModelSupplier("/models"));
		
		Namespace ns4 = new Namespace("expt-metadata", "https://localhost", "1.0.0");
		providers.put(ns4.getNamespaceName(), new FileDataModelSupplier("/sra_schemas"));
		
	    namespaces.add(ns1);
	    //namespaces.add(ns2);
	    namespaces.add(ns3);
	    namespaces.add(ns4);
	  }
	  
	  public SchemaProvider getProvider(String namespace) {
		  return providers.get(namespace);
	  }
	  
	  public List<Namespace> getNamespaces() {
		  return namespaces;
	  }
}
