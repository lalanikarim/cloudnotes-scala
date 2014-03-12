package models;

import java.util.*;

import javax.persistence.*;

import net.vz.mongodb.jackson.DBRef;

public class NoteItem{
	
	public Integer Id;
	
	//public Note ParentNote;
	
	public DBRef<User,String> Author;
	
	public Date CreatedOn;
	
	public String NoteText;
}
