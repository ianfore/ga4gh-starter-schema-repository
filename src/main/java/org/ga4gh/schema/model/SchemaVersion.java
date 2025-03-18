package org.ga4gh.schema.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SchemaVersion {

	  private String version;
	  private String status;
	  private List<String> contributors;
	  private String releaseDate;
	  private String releaseNotes;
	  
	  SchemaVersion() {}

	public SchemaVersion(String version, String status, List<String> contributors,
			String releaseDate, String releaseNotes) {

	    this.version = version;
	    this.status = status;
	    this.contributors = contributors;
	    this.releaseDate = releaseDate;
	    this.releaseNotes = releaseNotes;
	}
}


