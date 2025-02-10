package org.ga4gh.schema.model;

import java.util.Objects;

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
public class Namespace {

	  private String namespaceName;
	  private String latestVersion;
	  private String contact_url;
	  
	Namespace() {}

	public Namespace(String name, String latestVersion, String contact_url) {

	    this.namespaceName = name;
	    this.latestVersion = latestVersion;
    this.contact_url = contact_url;
  }


}
