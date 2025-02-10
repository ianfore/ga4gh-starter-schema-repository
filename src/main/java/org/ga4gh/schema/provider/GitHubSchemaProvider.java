package org.ga4gh.schema.provider;

import java.util.List;

import org.ga4gh.schema.model.Schema;

import com.dnastack.ga4gh.dataconnect.model.DataModel;

public class GitHubSchemaProvider implements SchemaProvider {

	private String repository;
	private List<String> schemaPaths;
	
	public GitHubSchemaProvider(String repository, List<String> schemaPaths) {
		this.repository = repository;
		this.schemaPaths = schemaPaths;
	}
	
	@Override
	public String supplyXML(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataModel supplySchema(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schema> getSchemas(String studyVersion) {
		// TODO Auto-generated method stub
		return null;
	}

}
