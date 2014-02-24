package models;

import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class NoteACL extends Model {

	@Id
	public Integer Id;
	
	@ManyToOne
	public Note ACLNote;
	
	@ManyToOne
	public User ACLUser;
	
	public NotePermission Permission;
	
	
	public enum NotePermission {
		ReadOnly,
		Edit,
		Author
	}
}
