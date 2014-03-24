package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBCollection;

import models.Note;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.*;
import play.libs.Json;
import play.modules.mongodb.jackson.MongoDB;
import play.modules.mongodb.jackson.MongoDBPlugin;
import play.mvc.*;
import views.html.*;


public class Application extends Controller {

    public static Result index() {
    	JacksonDBCollection<Note, String> coll = MongoDB.collection(Note.class, String.class, play.api.Play.current());
    	
    	return ok(index.render("Your notes application is ready.",coll.find().toArray(),NoteController.dynamicForm));
    }
    

    public static Result jsonnotes() {
    	return ok(views.html.json.render());
    }

}
