## SchemaRegistry pilot implementation

This project provides an exploratory implementation of the proposed GA4GH Schema Registry standard. The Schema Registry is a product of the GA4GH Data Model and Schema Consensus ([DaMaSC](https://www.ga4gh.org/product/data-model-and-schema-consensus-damasc/)) study group. The intent is to provide an implementation of the proposed functionality in a way that allows iterative revision of the standard by the study group. It attempts to address known sources of schema, or use cases originating from other GA4GH workstreams.

It is not an attempt to produce a production implementation of the standard.

### As a work in progress the following points apply
* Not all proposed methods are implemented
* Some additional corollary methods are provided which indicate functionality that may be required

## Specification and other relevant documents

* Active revisions are made in [Google Doc](https://docs.google.com/document/d/1SHXYxTzt8_8CC2kWbLrsA3L-nvwi3XuC6DOywLM_39o/edit?tab=t.0)
* [Schema Registry Specification](https://ga4gh.github.io/schema-registry/schema_registry_spec/)
* [Schema Registry Specification - GitHub](https://github.com/ga4gh/schema-registry/blob/main/docs/schema_registry_spec.md)


### Notebooks
 Jupyter notebooks demonstrating use of this implementation and the schema sources currently covered are available at [FASP Search](https://github.com/ga4gh/fasp-scripts/tree/master/notebooks/search)
 
### Included code
The files included in the com.dnastack.ga4gh.dataconnect package and its sub-packages are from the https://github.com/DNAstack/data-connect-trino repository and subject to the license present in that repository.