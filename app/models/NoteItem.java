package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class NoteItem extends Model {
	
	@Id
	public Integer Id;
	
	@ManyToOne
	public Note ParentNote;
	
	@ManyToOne
	public User Author;
	
	public Date CreatedOn;
	
	@Column(length = 1024)
	public String NoteText;
}
