package models

import play.api.libs.json.Json
import reactivemongo.bson._
;

case class User(UserId:String,FullName:String,Password:String)

object User {
  val fldUserId = "_id"
  val fldFullName = "fullname"
  val fldPassword = "password"

  implicit val userFormat = Json.format[User]

  implicit object UserWriter extends BSONDocumentWriter[User] {
    def write(user:User) = BSONDocument(
      fldUserId -> user.UserId,
      fldFullName -> user.FullName,
      fldPassword -> user.Password
    )
  }

  implicit object UserReader extends BSONDocumentReader[User] {
    def read(doc: BSONDocument) = User(
      doc.getAs[String](fldUserId).getOrElse(""),
      doc.getAs[String](fldFullName).getOrElse(""),
      doc.getAs[String](fldPassword).getOrElse("")
    )
  }
}