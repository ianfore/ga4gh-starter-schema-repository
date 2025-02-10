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
public class Schema {

	  private String schemaName;
	  private String latestVersion;
	  private List<String> maintainer;
	  private String lifecycleStage;
	  
	  Schema() {}

	public Schema(String schemaName, String latestVersion, List<String> maintainer, String lifecycleStage) {

	    this.schemaName = schemaName;
	    this.latestVersion = latestVersion;
	    this.maintainer = maintainer;
	    this.lifecycleStage = lifecycleStage;

  }
}
