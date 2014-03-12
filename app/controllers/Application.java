package controllers;

import com.mongodb.DBCollection;

import models.Note;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.*;
import play.modules.mongodb.jackson.MongoDB;
import play.modules.mongodb.jackson.MongoDBPlugin;
import play.mvc.*;
import views.html.*;


public class Application extends Controller {

    public static Result index() {
    	//org.h2.Driver
    	JacksonDBCollection<Note, String> coll = MongoDB.collection(Note.class, String.class, play.api.Play.current());
    	
    	return ok(index.render("Your new application is ready.",coll.find().toArray(),NoteController.dynamicForm));
    }

}
