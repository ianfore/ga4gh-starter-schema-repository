package org.ga4gh.schema.provider;

import java.util.List;

import org.ga4gh.schema.dbgap.dict.DataDict;
import org.ga4gh.schema.model.Schema;

import com.dnastack.ga4gh.dataconnect.model.DataModel;

public interface SchemaProvider {

	String supplyXML(String tableName);

	DataModel supplySchema(String tableName);

	List<Schema> getSchemas(String studyVersion);

}