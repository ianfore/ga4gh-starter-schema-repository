package org.ga4gh.schema.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@JsonView(SerializeView.Public.class)
public class SchemaResponse {

	  private String namespace;
	  private List<Schema> schemas;

	  
	public SchemaResponse(String namespace, List<Schema> schemas) {

	    this.namespace = namespace;
	    this.schemas = schemas;

  }
}

