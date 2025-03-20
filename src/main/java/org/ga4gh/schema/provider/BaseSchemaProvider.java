package org.ga4gh.schema.provider;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
//@ConfigurationProperties("app.file-datamodel-supplier")
public class BaseSchemaProvider {

    
    public BaseSchemaProvider() {
    }

	public List<String> getReturnTypes() {
		List<String> returnTypes = Arrays.asList("application/schema+json");
		return returnTypes;
	}
}
