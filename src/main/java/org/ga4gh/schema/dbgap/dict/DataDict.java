package org.ga4gh.schema.dbgap.dict;

import lombok.Getter;
//import lombok.Setter;

//@Setter
@Getter
public class DataDict {
	private String name;
	private String pht;
	private String version;
	private String study;
	private String studyVersion;
	private String dictFileName;
	private String dictFullName;

	public DataDict(String name, String pht, String version) {
		this.name = name;
		this.version = version;
		this.pht = pht;
	}
	
	public DataDict(String dictFileName) {
    	String[] parts = dictFileName.split("\\.");
       	this.dictFileName = dictFileName;
       	this.dictFullName = dictFileName.replace(".data_dict.xml", "");
       	this.study = parts[0];
       	this.studyVersion = parts[1];
       	this.pht = parts[2];
    	this.version = parts[3];
    	this.name = parts[4];
	}
	
	public String getPath() {
		return "/dbgap/studies/" + study + "/" + study + "." + studyVersion + ".p1/pheno_variable_summaries/" + dictFileName + ".data_dict.xml";
	}
}

