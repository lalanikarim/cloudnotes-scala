package controllers

import play.api.data.Form
import play.api.mvc._
import play.modules.reactivemongo._
//import play.twirl.api.Html
import reactivemongo.api.collections.default._
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Application extends Controller with MongoController {

    lazy val coll = db("notes")

    def index() = Action.async { implicit response =>
      coll.find(BSONDocument()).cursor[models.Note].collect[List]().map{
          notes =>
            Ok(views.html.index("Your notes application is ready.")(notes))
      }
    	//return ok(views.html.index("Your notes application is ready."),coll.find().toArray(),NoteController.dynamicForm));
    }
    

    def jsonnotes() = Action {
    	Ok(views.html.json())
    }

}
