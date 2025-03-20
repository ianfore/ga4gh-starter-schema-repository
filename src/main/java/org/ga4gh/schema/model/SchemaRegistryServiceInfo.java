package org.ga4gh.schema.model;

import org.ga4gh.starterkit.common.model.ServiceInfo;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.ID;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.NAME;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.DESCRIPTION;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.CONTACT_URL;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.DOCUMENTATION_URL;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.CREATED_AT;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.UPDATED_AT;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.ENVIRONMENT;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.VERSION;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.ORGANIZATION_NAME;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.ORGANIZATION_URL;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.SERVICE_TYPE_GROUP;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.SERVICE_TYPE_ARTIFACT;
import static org.ga4gh.schema.constant.ServiceRegistryServiceInfoDefaults.SERVICE_TYPE_VERSION;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Extension of the GA4GH base service info specification to include Schema Registry specific
 * properties
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class SchemaRegistryServiceInfo extends ServiceInfo {

    /**
     * Instantiates a new SchemaRegistryServiceInfo object
     */
    public SchemaRegistryServiceInfo() {
        super();
        setAllDefaults();
    }

    /**
     * Sets all default properties
     */
    
    private void setAllDefaults() {
    	setId(ID);
        setName(NAME);
        setDescription(DESCRIPTION);
        setContactUrl(CONTACT_URL);
        setDocumentationUrl(DOCUMENTATION_URL);
        setCreatedAt(CREATED_AT);
        setUpdatedAt(UPDATED_AT);
        setEnvironment(ENVIRONMENT);
        setVersion(VERSION);
        getOrganization().setName(ORGANIZATION_NAME);
        getOrganization().setUrl(ORGANIZATION_URL);
        getType().setGroup(SERVICE_TYPE_GROUP);
        getType().setArtifact(SERVICE_TYPE_ARTIFACT);
        getType().setVersion(SERVICE_TYPE_VERSION);
    }
}