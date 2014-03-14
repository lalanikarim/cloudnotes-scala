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
		
		Note newNote = new Note();
		newNote.Title = "New Note";
		notes.save(newNote).getSavedId();
		//newNote = notes.findOneById(id);
		
		return redirect("/");
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
	
	public static Result savedimension(){
		Map<String,String> data = dynamicForm.bindFromRequest().data();
		String id = data.get("id");
		String height = data.get("height");
		String width = data.get("width");
		
		notes.updateById(id, DBUpdate.set("height", Math.floor(Double.parseDouble(height))).set("width", Math.floor( Double.parseDouble(width))));
		return redirect("/");
	}

	public static Result saveitem(String noteid){
		Map<String,String> data = dynamicForm.bindFromRequest().data();
		String text = data.get("notetext");
		
		int itemid = 0;
		String itemidstr = data.get("itemid");
		if(itemidstr != null && isNumeric(itemidstr)){
			itemid = Integer.parseInt(itemidstr);
		}
		Note fromDB = notes.findOneById(noteid);
		NoteItem item = null;
	
		if(itemid == 0){
			if(text.length() > 0){
				item = new NoteItem();
				item.Id = fromDB.Items.size()+1;
				fromDB.Items.add(item);
			}
		} else {	
			
			for(NoteItem ni : fromDB.Items){
				if(ni != null && ni.Id != null && ni.Id == itemid){
					item = ni;
					break;
				}
			}			
		}
		if(item != null){
			if(text.length() > 0){
				item.NoteText = text;
			} else {
				fromDB.Items.remove(item);
			}
			notes.save(fromDB);
		}
		return redirect("/");
	}
    public static boolean isNumeric(String s) {  
        return java.util.regex.Pattern.matches("\\d+", s);  
    }  
}
