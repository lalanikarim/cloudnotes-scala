package models;

import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class User extends Model {

	@Id
	public String UserId;
	
	public String Password;
}
