package org.ga4gh.schema.provider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ga4gh.schema.dbgap.dict.DataDict;
import org.ga4gh.schema.model.Schema;
import org.ga4gh.schema.model.SchemaVersion;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

import com.dnastack.ga4gh.dataconnect.model.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GitHubSchemaProvider extends BaseSchemaProvider implements SchemaProvider {

	private String repository;
	private List<String> schemaPaths;
	private GHRepository repo;
	private String schemaBase;

    private final ObjectMapper objectMapper = new ObjectMapper();

	public GitHubSchemaProvider(String repository, String schemaBase) {
		repository = repository;
		//this.schemaPaths = schemaPaths;
		repo = null;
		this.schemaBase = schemaBase;
		
		
		//GitHub github = GitHub.connect();
		GitHub github;
		try {
			github = GitHub.connectAnonymously();
	        GHRepository repo = github.getRepository(repository);
	        this.repo = repo;
	        System.out.println("GitHub repo is: " + repo.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String supplyXML(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataModel supplySchema(String schemaName) {
		DataModel model = null;
		try {
			GHContent file = repo.getFileContent(schemaBase + "/" + schemaName);
			InputStream inputStream = file.read();
			//schema = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
			model = objectMapper.readValue(inputStream, DataModel.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	@Override
	public List<Schema> getSchemas(String studyVersion) {
		//String schemaPath = schemaPaths.get(0);
		try {
			List<GHContent> contents = repo.getDirectoryContent(this.schemaBase);
			//List<GHContent> contents = repo.getDirectoryContent(schemaPath);
			List<Schema> schemas = new ArrayList<>();
			GHUser owner = repo.getOwner();
			List<String> maintainers = Arrays.asList(owner.getName());
			for (GHContent content : contents) {
				//schemas.add(new Schema(content.getName(), content.getSha(),  maintainers, "released"));
				schemas.add(new Schema(content.getName(), "1.x",  maintainers, "released"));
			  }
			return schemas;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SchemaVersion> getSchemaVersions(String schema) {
		List<SchemaVersion> versions = new ArrayList<>();
		try {
			PagedIterable<GHTag> listTags = repo.listTags();

			GHUser owner = repo.getOwner();
			List<String> maintainers = Arrays.asList(owner.getName());
			
			List<GHTag> tags = listTags.toList();
			for (GHTag tag : tags) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			    String formattedDate = dateFormat.format(tag.getCommit().getCommitDate());
			    String tagName = tag.getName();
			    SchemaVersion v = new SchemaVersion(tagName, getStatus(tagName), maintainers,
			    		formattedDate, //releaseDate
			    		"" ); //String releaseNotes
				versions.add(v);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versions;
	}
	
	private String getStatus(String version) {
	  String status = "unknown";
	  String pattern = "(\\d+\\.\\d+\\.\\d+)(.*)";
	  
	  Pattern r = Pattern.compile(pattern);
	  Matcher m = r.matcher(version);
	  
	  if (m.find( )) {
			/*
			 * System.out.println("full version: " + m.group(0) );
			 * System.out.println("numeric version: " + m.group(1) );
			 * System.out.println("remainder: " + m.group(2) );
			 */	         
		 status = m.group(2);
	     if (status.length() == 0) status = "released"; 
	     else status = "development";;
	  } else {
	     //System.out.println("NO MATCH");
	  }
	  return status;
	}
	
	/*public List<String> getReturnTypes() {
		List<String> returnTypes = Arrays.asList("json");
		return returnTypes;
	}*/

}
