package org.ga4gh.schema.constant;

import java.time.LocalDateTime;

import org.ga4gh.starterkit.common.constant.DateTimeConstants;

/**
 * Default values for the DRS service info response
 */
public class ServiceRegistryServiceInfoDefaults {

    /**
     * Default service id
     */
    public static final String ID = "org.ga4gh.schema-registry";

    /**
     * Default service name
     */
    public static final String NAME = "Experimental GA4GH Schema Registry";

    /**
     * Default service description
     */
    public static final String DESCRIPTION = "An open source, experimental, community-driven"
        + " implementation of the GA4GH Schema Registry"
        + " API specification for the purpose of revising the API specification.";

    /**
     * Default service contact URL
     */
    public static final String CONTACT_URL = "mailto:info@ga4gh.org";

    /**
     * Default service documentation URL
     */
    public static final String DOCUMENTATION_URL = "https://github.com/ga4gh/schema-registry";

    /**
     * Default service creation/launch time
     */
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2025-03-19T12:00:00Z", DateTimeConstants.DATE_FORMATTER);

    /**
     * Default service last updated time
     */
    public static final LocalDateTime UPDATED_AT = LocalDateTime.parse("2025-03-19T12:00:00Z", DateTimeConstants.DATE_FORMATTER);;

    /**
     * Default service environment
     */
    public static final String ENVIRONMENT = "test";

    /**
     * Default service version
     */
    public static final String VERSION = "0.0.1";

    /**
     * Default service organization name
     */
    public static final String ORGANIZATION_NAME = "Global Alliance for Genomics and Health";

    /**
     * Default service organization URL
     */
    public static final String ORGANIZATION_URL = "https://ga4gh.org";

    /**
     * Default service type group
     */
    public static final String SERVICE_TYPE_GROUP = "org.ga4gh";

    /**
     * Default service type artifact
     */
    public static final String SERVICE_TYPE_ARTIFACT = "schema-registry";

    /**
     * Default service type version
     */
    public static final String SERVICE_TYPE_VERSION = "0.0.1.experimental";
}