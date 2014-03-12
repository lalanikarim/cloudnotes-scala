package models;

import java.util.*;

import org.codehaus.jackson.annotate.*;
import net.vz.mongodb.jackson.*;

@MongoCollection(name = "notes")
public class Note {

	@ObjectId
	@Id
	public String Id;
	
	@JsonProperty("title")
	public String Title;
	
	@JsonProperty("items")
	public List<NoteItem> Items = new ArrayList<NoteItem>();
	
	@JsonProperty("acl")
	public List<NoteACL> ACL = new ArrayList<NoteACL>();
	
	@JsonProperty("width")
	public Integer Width;
	
	@JsonProperty("height")
	public Integer Height;
	
	@JsonProperty("position")
	public Integer Position;
	
	@JsonProperty("left")
	public Integer Left;
	
	@JsonProperty("top")
	public Integer Top;
	

}
