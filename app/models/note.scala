package models;

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.json.{Format, Json, Reads, Writes}
import reactivemongo.bson._
import reactivemongo.play.json._


case class Note (
									Id:Option[BSONObjectID],
									Title:String,
									Items:List[NoteItem],
									//ACL:List[NoteACL],
									Width:Int,
									Height:Int,
									Position:Int,
									Left:Int,
									Top:Int)

object Note {

	val fldId = "_id"
	val fldTitle = "title"
	val fldItems = "items"
	val fldACL = "acl"
	val fldWidth = "width"
	val fldHeight = "height"
	val fldPosition = "position"
	val fldLeft = "left"
	val fldTop = "top"


	implicit val notesFormat = new Writes[Note]{
		def writes(note:Note) = {
			Json.obj(fldId ->
				{
					note.Id match {
						case Some(bid) => bid.stringify
						case None => ""
					}},
				fldTitle -> note.Title,
				fldItems -> note.Items,
				fldWidth -> note.Width,
				fldHeight -> note.Height,
				fldPosition -> note.Position,
				fldLeft -> note.Left,
				fldTop -> note.Top
			)
		}
	}

	implicit object NoteWriter extends BSONDocumentWriter[Note] {
		def write(note:Note):BSONDocument = BSONDocument(
			fldId -> note.Id.getOrElse(BSONObjectID.generate),
			fldTitle -> note.Title,
			fldItems -> note.Items,
			//fldACL -> note.ACL,
			fldWidth -> note.Width,
			fldHeight -> note.Height,
			fldPosition -> note.Position,
			fldLeft -> note.Left,
			fldTop -> note.Top
		)
	}

	implicit object NoteReader extends BSONDocumentReader[Note] {
		def read(doc:BSONDocument) = Note(
			doc.getAs[BSONObjectID](fldId),
			doc.getAs[String](fldTitle).getOrElse(""),
			doc.getAs[List[NoteItem]](fldItems).getOrElse(List()),
			//doc.getAs[List[NoteACL]](fldACL).getOrElse(List()),
			doc.getAs[Int](fldWidth).getOrElse(0),
			doc.getAs[Int](fldHeight).getOrElse(0),
			doc.getAs[Int](fldPosition).getOrElse(0),
			doc.getAs[Int](fldLeft).getOrElse(0),
			doc.getAs[Int](fldTop).getOrElse(0)
		)
	}

	val titleForm = Form(
		mapping(
			"id" -> nonEmptyText,
			fldTitle -> nonEmptyText
		)
		{(id,title) => (id -> title)}
		{form =>
			val (id,title) = form
			Some(id,title)}
	)

	val itemForm = Form(
		mapping(
			"itemid" -> number,
			"notetext" -> nonEmptyText
		)
		{(itemid,notetext) => (itemid -> notetext)}
		{form =>
			val (id,text) = form
			Some(id,text)}
	)

	val positionForm = Form(
		mapping(
			"id" -> nonEmptyText,
			fldLeft -> number,
			fldTop -> number
		)
		{(id,left,top) => Tuple3(id,left,top)}
		{form =>
			val (id,left,top) = form
			Some(id,left,top)}
	)

	val sizeForm = Form(
		mapping(
			"id" -> nonEmptyText,
			fldWidth -> number,
			fldHeight -> number
		)
		{(id,w,h) => Tuple3(id,w,h)}
		{form =>
			val (id,w,h) = form
			Some(id,w,h)}
	)
}
