package org.ga4gh.schema.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@JsonView(SerializeView.Public.class)
public class SchemaVersionsResponse {

	  private String schemaName;
	  private List<SchemaVersion> versions;

	  
	public SchemaVersionsResponse(String schemaName, List<SchemaVersion> versions) {

	    this.schemaName = schemaName;
	    this.versions = versions;

  }
}


