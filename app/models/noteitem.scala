package models

import java.util.Date

import play.api.libs.json.{Writes, Json}
import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter}
import BSONProducers._

case class NoteItem(Id:Int,
                     //Author:User,
                     //CreatedOn:java.util.Date,
                     NoteText:String)

object NoteItem {
  val fldId = "id"
  //val fldAuthor = "author"
  //val fldCreatedOn = "created"
  val fldNoteText = "notetext"

  implicit val noteitemFormat = new Writes[NoteItem]{
    def writes(noteitem:NoteItem) = {
      Json.obj(fldId -> noteitem.Id,fldNoteText -> noteitem.NoteText)
    }
  }
  //implicit val noteitemFormat = Json.format[NoteItem]

  implicit object NoteItemWriter extends BSONDocumentWriter[NoteItem] {
    def write(noteitem:NoteItem) = BSONDocument(
      fldId -> noteitem.Id,
      //fldAuthor -> noteitem.Author,
      //fldCreatedOn -> noteitem.CreatedOn,
      fldNoteText -> noteitem.NoteText
    )
  }

  implicit object NoteItemReader extends BSONDocumentReader[NoteItem]{
    def read(doc:BSONDocument) = NoteItem(
      doc.getAs[Int](fldId).getOrElse(0),
      //doc.getAs[User](fldAuthor).getOrElse(User("Not Found","Not Found","")),
      //doc.getAs[java.util.Date](fldCreatedOn).getOrElse(new Date()),
      doc.getAs[String](fldNoteText).getOrElse("")
    )
  }
}