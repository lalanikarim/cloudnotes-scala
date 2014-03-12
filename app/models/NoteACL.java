package models;

import javax.persistence.*;

import net.vz.mongodb.jackson.DBRef;

public class NoteACL{

	//public Integer Id;
	
	//public Note ACLNote;
	
	//public User ACLUser;
	
	public DBRef<User, String> ACLUser;

	public NotePermission Permission;
	
	public enum NotePermission {
		ReadOnly,
		Edit,
		Author
	}
}
