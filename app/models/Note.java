package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Note extends Model {

	@Id
	public Integer Id;
	
	@OneToMany
	public List<NoteItem> Items = new ArrayList<NoteItem>();
	
	@OneToMany
	public List<NoteACL> ACL = new ArrayList<NoteACL>();
	
	public Integer Width;
	
	public Integer Height;
	
	public Integer Position;

}
