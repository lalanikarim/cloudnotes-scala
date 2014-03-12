package models;

import org.codehaus.jackson.annotate.*;

import net.vz.mongodb.jackson.*;

@MongoCollection(name="users")
public class User{

	@ObjectId
	@Id
	public String UserId;
	
	public String FullName;
	
	public String Password;
}
