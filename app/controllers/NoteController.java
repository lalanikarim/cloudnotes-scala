package controllers;

import java.util.Map;

import models.Note;
import models.NoteItem;
import net.vz.mongodb.jackson.DBUpdate;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.*;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import play.data.Form;
import play.modules.mongodb.jackson.MongoDB;
import play.mvc.*;
import views.html.note.*;

public class NoteController extends Controller {
	
	public static DynamicForm dynamicForm = Form.form();
	private static JacksonDBCollection<models.Note, String> notes = MongoDB.collection(models.Note.class, String.class,play.api.Play.current());
	public static Result create() {
		
		models.Note newNote = new models.Note();
		String id = notes.save(newNote).getSavedId();
		newNote = notes.findOneById(id);
		
		return ok(index.render(newNote));
	}
	
	public static Result get(Integer id){
		return ok();
	}
	
	public static Result save(){
		Map<String,String> data = dynamicForm.bindFromRequest().data();
		
			String id = data.get("id").toString();
			String title = data.get("title").toString();
			Note fromDB = notes.findOneById(id);
			fromDB.Title = title;
			
			notes.save(fromDB);
		
		return redirect("/");
	}
	
	public static Result saveposition(){
		Map<String,String> data = dynamicForm.bindFromRequest().data();
		String id = data.get("id");
		String left = data.get("left");
		String top = data.get("top");
		
		notes.updateById(id, DBUpdate.set("left", Math.floor(Double.parseDouble(left))).set("top", Math.floor( Double.parseDouble(top))));
		return redirect("/");
	}

	public static Result saveitem(String noteid){
		Map<String,String> data = dynamicForm.bindFromRequest().data();
		String text = data.get("notetext");
		int itemid = Integer.parseInt(data.get("itemid"));
		Note fromDB = notes.findOneById(noteid);
		NoteItem item = null;
	
		if(fromDB.Items.size() == 0)
		{
			item = new NoteItem();
		}
		else
		{
			for(NoteItem ni : fromDB.Items){
				if(ni != null && ni.Id != null && ni.Id == itemid){
					item = ni;
					break;
				}
			}
			if(item == null){
				item = new NoteItem();
			}
		}
		item.NoteText = text;
		fromDB.Items.add(item);
		notes.save(fromDB);
		return redirect("/");
	}
}
