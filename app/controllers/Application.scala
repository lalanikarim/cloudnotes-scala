package controllers

import play.api.data.Form
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.Play.current
import reactivemongo.play.json.collection.JSONCollection
import javax.inject.Inject
import play.api.libs.json._
import reactivemongo.play.json._
import reactivemongo.api.Cursor
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection._
import reactivemongo.api.collections.bson.BSONCollection

class Application @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

    lazy val coll =  db.collection[BSONCollection]("notes")

    def index() = Action.async { implicit response =>
      coll.find(BSONDocument()).cursor[models.Note].collect[List]().map{
          notes =>
            Ok(views.html.index("Your notes application is ready.")(notes))
      }
    }

    def jsonnotes() = Action {
    	Ok(views.html.json())
    }

}
