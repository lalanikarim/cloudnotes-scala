package controllers;

import play.api.data.Form
import play.api.mvc._
import play.modules.reactivemongo._
import play.twirl.api.Html
import reactivemongo.api._
import reactivemongo.api.collections.default._
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.libs.json._
import play.api.libs.functional.syntax._



object NoteController extends Controller with MongoController {

	lazy val notes = db("notes")

	def create() = Action.async { implicit response =>
		val newNote = models.Note(None,"New Note",List(),0,0,0,0,0)
		notes.save(newNote).map{
			result =>
				Redirect("/")
		}
	}

	def save = Action.async { implicit request =>
		models.Note.titleForm.bindFromRequest().fold(
			hasErrors => Future successful BadRequest,
			idtitle => {
				val (id, title) = idtitle
				val selector = BSONDocument("_id" -> BSONObjectID(id))
				for {
					result <- notes.update(selector,BSONDocument("$set" -> BSONDocument("title" -> title)))
					optnote <-	notes.find(selector).one[models.Note] if result.ok == true
				} yield {
					optnote match {
						case Some(note) => Ok(Json.toJson(note))
						case None => BadRequest
					}
				}
			}
		)
	}
	
	def delete(noteid:String) = Action.async {
		notes.remove(BSONDocument("_id" -> BSONObjectID(noteid))).map{
			result =>
				Redirect("/")
		}
	}
	
	def saveposition = Action.async { implicit request =>

		models.Note.positionForm.bindFromRequest().fold(
			hasErrors => Future successful Ok,
			data => {
				val (id, left, top) = data
				val selector = BSONDocument("_id" -> BSONObjectID( id))
				for {
					result <- notes.update(selector,BSONDocument("$set" -> BSONDocument("left" -> left,"top" -> top)))
					optnote <- notes.find(selector).one[models.Note] if result.ok == true
				} yield {
					optnote match {
						case Some(note) => Ok(Json.toJson(note))
						case None => BadRequest
					}
				}
			}
		)
	}
	
	def savedimension = Action.async { implicit request =>

		models.Note.sizeForm.bindFromRequest().fold(
			hasErrors => Future successful Ok,
			data => {
				val (id, width, height) = data
				val selector = BSONDocument("_id" -> BSONObjectID( id))
				for{
					result <- notes.update(BSONDocument("_id" -> BSONObjectID(id)),BSONDocument("$set" -> BSONDocument("width" -> width,"height" -> height)))
					optnote <- notes.find(selector).one[models.Note] if result.ok == true
				} yield {
					optnote match {
						case Some(note) => Ok(Json.toJson(note))
						case None => BadRequest
					}
				}
			}
		)
	}
	
	def getNote(noteid:String ) = Action.async { implicit request =>
		notes.find(BSONDocument("_id" -> BSONObjectID(noteid))).one[models.Note].map {
				case Some(note) => Ok(Json.toJson(note))
				case None => NoContent
		}
	}

	def saveitem(noteid:String) = Action.async { implicit request =>
		models.Note.itemForm.bindFromRequest().fold(
			hasErrors => Future successful BadRequest,
			data => {
				val (id, notetext) = data
				val selector = BSONDocument("_id" -> BSONObjectID(noteid))

				if(id > 0) {
					if (notetext.length > 0) {
						for {
							result <- notes.update(selector ++ ("items.id" -> id), BSONDocument("$set" -> BSONDocument("items.$.notetext" -> notetext)))
							optnote <- notes.find(selector).one[models.Note] if result.ok
						} yield {
							optnote match {
								case Some(note) => Ok(Json.toJson(note))
								case None => NoContent
							}
						}
					}
					else {
						for {
							result <- notes.update(selector, BSONDocument("$pull" -> BSONDocument("items" -> BSONDocument("id" -> id))))
							optnote <- notes.find(selector).one[models.Note] if result.ok
						} yield {
							optnote match {
								case Some(note) => Ok(Json.toJson(note))
								case None => NoContent
							}
						}
					}
				}
				else {

					for {
						orignote <- notes.find(selector).one[models.Note]
						result <- notes.update(selector,
							BSONDocument(
								"$push" -> BSONDocument(
									"items" -> BSONDocument(
										"id" -> {
											val items = orignote.get.Items
											if(items.length > 0)
												items.map{_.Id}.max + 1
											else
												1
										}, "notetext" -> notetext)))) if orignote.isDefined
						optnote <- notes.find(selector).one[models.Note] if result.ok
					} yield {
						optnote match {
							case Some(note) => Ok(Json.toJson(note))
							case None => NoContent
						}
					}
				}
			}
		)
	}

/*
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
		com.fasterxml.jackson.databind.JsonNode result = Json.toJson(fromDB);
		//if(isXmlHttp()){
			return ok(result);
		//} else {
		//	return redirect("/");
		//}
	}*/
    def isNumeric(s:String) = {
        java.util.regex.Pattern.matches("\\d+", s)
    }  
    
    def json = Action.async {
    	notes.find(BSONDocument()).cursor[models.Note].collect[List]().map{
				list =>
					Ok(Json.toJson(list))
			}
    }
    
    private def isXmlHttp(request:Request[AnyContent]) = {
    	request.headers.get("X-Requested-With") match {
				case Some("XMLHttpRequest") => true
				case _ => false
			}
    }
}
